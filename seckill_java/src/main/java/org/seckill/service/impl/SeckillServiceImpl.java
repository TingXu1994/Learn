package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import org.seckill.dao.SeckillDAO;
import org.seckill.dao.SuccessKilledDAO;
import org.seckill.dto.Excution;
import org.seckill.dto.Exposer;
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
import org.springframework.util.DigestUtils;

@Service
public class SeckillServiceImpl implements SeckillService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final String md5XX = "sgfjasg89320-1s0`283018(()@}G:~~??";

	@Autowired
	private SeckillDAO seckillDAO;

	@Autowired
	private SuccessKilledDAO successKilledDAO;

	public Seckill getSeckillById(long seckillId) {
		return seckillDAO.queryById(seckillId);
	}

	public List<Seckill> getAllSeckills(int offset, int limit) {
		return seckillDAO.queryAll(offset, limit);
	}

	private String getMd5(long seckillId) {
		String baseString = seckillId + md5XX;
		return DigestUtils.md5DigestAsHex(baseString.getBytes());
	}

	public Exposer getExposeEntity(long seckillId) {
		Seckill seckill = seckillDAO.queryById(seckillId);
		if (seckill == null)
			return new Exposer(false, seckillId);

		Date nowTime = new Date();
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		/**
		 * ？--存疑：getTime的效率问题--？
		 */
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime())
			return new Exposer(false, seckillId);
		else {
			String md5String = getMd5(seckillId);
			return new Exposer(true, seckillId, md5String);
		}
	}

	public Excution excuteSeckill(long seckillId, Long userPhone, String md5)
			throws SeckillException, SeckillClosedException, RepeatKillException {
		Date nowTime = new Date();

		/**
		 * ?--存疑：逻辑上md5应该是和对应seckillId的Exposer的md5String比较;
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
					throw new SeckillClosedException("seckill is closed");
				} else {
					// 暂且不明白这个Excution的作用，存储执行状态吗？
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

}
