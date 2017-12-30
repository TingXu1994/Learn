package org.seckill.dao;

import javax.annotation.Resource;
import org.junit.Test;
import org.seckill.BaseTest;
import org.seckill.entity.SuccessKilled;

public class SuccessKilledDAOTest extends BaseTest{

	@Resource
	private SuccessKilledDAO successkilledDAO;

	@Test
	public void testInsertSuccessKilled() {
		long id = 1000L;
		long userphone = 18810623333L;
		int i = successkilledDAO.insertSuccessKilled(id, userphone);
		// insert ignore,把sql的主键异常转化为返回值1或者0表示插入成功还是失败
		System.out.println("插入了" + i + "行");
	}

	@Test
	public void testQueryByIdWithSeckill() {
		SuccessKilled successKilled = successkilledDAO.queryByIdWithSeckill(1000L,18810623333L);
		System.out.println(successKilled);
		System.out.println(successKilled.getSeckill());
	}

}
