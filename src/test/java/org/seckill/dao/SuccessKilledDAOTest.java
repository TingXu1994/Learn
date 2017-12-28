package org.seckill.dao;

import static org.junit.Assert.*;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDAOTest {

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
