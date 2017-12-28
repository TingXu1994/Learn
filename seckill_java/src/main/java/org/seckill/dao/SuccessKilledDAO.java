package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

public interface SuccessKilledDAO {
	/**
	 * 插入购买明细到Success_get表
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @return 插入了多少行
	 */

	int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
	// 同类型需要使用Param指定参数
	// int insertSuccessKilled(long seckillId, long userPhone);

	/**
	 * 根据ID查询SuccessKilled并携带秒杀产品对象实体
	 * 
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}