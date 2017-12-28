package org.seckill.dao;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDAO;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDAOTest extends TestCase {

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
