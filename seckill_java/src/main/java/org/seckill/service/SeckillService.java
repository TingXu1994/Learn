package org.seckill.service;

/**
 * 存疑的部分：excuteSeckill函数的md5，是由seckillId生成的；（虽然另外一串扰乱码不知道，但安全性？）
 */
import java.util.List;

import org.seckill.dto.Excution;
import org.seckill.dto.Exposer;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;

public interface SeckillService {

	// 按照id查询秒杀商品
	Seckill getSeckillById(long seckillId);

	// 查询所有秒杀商品结果
	List<Seckill> getAllSeckills(int offset, int limit);

	// 获取内部暴露的秒杀接口
	Exposer getExposeEntity(long seckillId);

	// 执行秒杀并返回执行结果
	Excution excuteSeckill(long seckillId, Long userPhone, String md5)
			throws SeckillException, SeckillClosedException, RepeatKillException;
	
	// 通过存储过程 执行秒杀操作
	Excution excuteSeckillByProcedure(long seckillId, Long userPhone, String md5)
			throws SeckillException, SeckillClosedException, RepeatKillException;

}
