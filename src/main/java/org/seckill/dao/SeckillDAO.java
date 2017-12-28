package org.seckill.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

//与resources文件夹的mapper文件对应。实现的sql配置
public interface SeckillDAO {
	/**
	 * 减库存
	 * 
	 * @param seckillId
	 * @param killTime
	 * @return 表示更新的记录行数
	 */
	int reduceNum(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

	/**
	 * 根据ID查询
	 * 
	 * @param seckillId
	 * @return
	 */
	Seckill queryById(long seckillId);

	/**
	 * 偏移量查询秒杀商品列表
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

}
