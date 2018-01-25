package org.seckill.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.JedisDao;
import org.seckill.dao.SeckillDAO;
import org.seckill.dao.SuccessKilledDAO;
import org.seckill.dto.Excution;
import org.seckill.dto.Exposer;
import org.seckill.dto.ResponseResult;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String md5XX = "sgfjasg89320-1s0`283018(()@}G:~~??";

	@Autowired
	private SeckillDAO seckillDAO;

	@Autowired
	private JedisDao jedisDao;

	@Autowired
	private SuccessKilledDAO successKilledDAO;

	@Override
	public Seckill getSeckillById(long seckillId) {
		Seckill seckill = jedisDao.getSeckill(seckillId);
		if (seckill == null) {
			seckill = seckillDAO.queryById(seckillId);
			if (seckill != null)
				jedisDao.setSeckill(seckill);
		}
		return seckill;
	}

	@Override
	public List<Seckill> getAllSeckills(int offset, int limit) {
		return seckillDAO.queryAll(offset, limit);
	}

	private String getMd5(long seckillId) {
		String baseString = seckillId + md5XX;
		return DigestUtils.md5DigestAsHex(baseString.getBytes());
	}

	@Override
	public Exposer getExposeEntity(long seckillId) {
		// Seckill seckill = seckillDAO.queryById(seckillId);

		Seckill seckill = this.getSeckillById(seckillId);
		if (seckill == null)
			return new Exposer(false, seckillId);

		Date nowTime = new Date();
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		/**
		 * TODO:--存疑：getTime的效率问题--？
		 */
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime())
			return new Exposer(false, seckillId);
		else {
			String md5String = getMd5(seckillId);
			return new Exposer(true, seckillId, md5String);
		}
	}

	// TODO:transactional声明式事务
	@Transactional
	@Override
	public Excution excuteSeckill(long seckillId, Long userPhone, String md5)
			throws SeckillException, SeckillClosedException, RepeatKillException {
		Date nowTime = new Date();

		/**
		 * TODO:--存疑：逻辑上md5应该是和对应seckillId的Exposer的md5String比较;
		 * 但是这里也可以用getMd5(seckillId)来比较； 应该怎么规范？
		 */
		if (!md5.equals(getExposeEntity(seckillId).getMd5String()))
			throw new SeckillException("seckill rewrite exception");
		if (md5 == null || !getExposeEntity(seckillId).isExpose())
			throw new SeckillException("seckill exception");

		try {
			// 插入successKilled表格记录;
			int insertCount = successKilledDAO.insertSuccessKilled(seckillId, userPhone);
			if (insertCount <= 0) {
				throw new RepeatKillException("seckill repeat exception");
			} else {
				// 若上面成功，则继续执行数据库表格Seckill减库存操作;
				int updateCount = seckillDAO.reduceNum(seckillId, nowTime);
				if (updateCount <= 0) {
					// rollback
					throw new SeckillClosedException("seckill is closed");
				} else {
					// commit
					SuccessKilled successKilled = successKilledDAO.queryByIdWithSeckill(seckillId, userPhone);
					return new Excution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
				}
			}
		} catch (RepeatKillException e1) {
			throw e1;
		} catch (SeckillClosedException e2) {
			throw e2;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 所有编译器的异常都转化为运行时异常
			throw new SeckillException("seckill inner error:" + e.getMessage());
		}
	}

	@Override
	public Excution excuteSeckillByProcedure(long seckillId, Long userPhone, String md5) {
		if (md5 == null || !md5.equals(this.getMd5(seckillId)))
			// 修正一个逻辑错误：不应该返回excution而是throw异常
			throw new SeckillException("seckill rewrite exception");

		Date seckillTime = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seckillId", seckillId);
		map.put("userPhone", userPhone);
		map.put("seckillTime", seckillTime);
		map.put("result", null);
		try {
			seckillDAO.excuteSeckillByProcedure(map);
			// 通过MapUtils获取seckill.sql执行存储过程的result的值(result在spring-dao.xml中配置)
			int result = MapUtils.getInteger(map, "result");
			if (result == 1) {
				SuccessKilled successKilled = successKilledDAO.queryByIdWithSeckill(seckillId, userPhone);
				return new Excution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
			} else {
				// return new Excution(seckillId, SeckillStateEnum.indexof(result));
				// 同理：和上面的逻辑一样，这里应该抛出异常
				throw new SeckillException("seckill exception");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new Excution(seckillId, SeckillStateEnum.INNER_ERROR);
		}
	}

}
