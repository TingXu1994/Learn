package org.seckill.dao;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.seckill.BaseTest;
import org.seckill.dao.SeckillDAO;
import org.seckill.entity.Seckill;

public class SeckillDAOTest extends BaseTest {

	@Resource
	private SeckillDAO seckilldao;

	@Test
	public void testQueryById() {
		long id = 1000;
		Seckill seckill = seckilldao.queryById(id);
		System.out.println(seckill.getName());
		System.out.println(seckill);
	}

	@Test
	public void testReduceNum() {
		//相同参数的需要在DAO接口中增加@Param注解；下面同理
		Date killTime = new Date(0);
		int i = seckilldao.reduceNum(1000L, killTime);
		System.out.println("更新库存" + i + "行");
	}

	@Test
	public void testQueryAll() {
		List<Seckill> seckills = seckilldao.queryAll(0, 10);
		for (Seckill seckill : seckills) {
			System.out.println(seckill);
		}
	}

}
