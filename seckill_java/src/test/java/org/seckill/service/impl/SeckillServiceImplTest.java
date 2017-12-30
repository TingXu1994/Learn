package org.seckill.service.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.seckill.BaseTest;
import org.seckill.dto.Excution;
import org.seckill.dto.Exposer;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SeckillServiceImplTest extends BaseTest {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// private SeckillServiceImpl seckillServiceImpl;
	// ？存疑：spring自动注解机制？是因为SeckillServiceImpl里的@Service标签吗？
	@Autowired
	private SeckillService seckillService;

	@Test
	public void testGetAllSeckills() {
		List<Seckill> seckills = seckillService.getAllSeckills(0, 3);
		logger.info("List={}", seckills);
	}

	@Test
	public void testGetSeckillById() {
		long seckillId = 1000L;
		Seckill seckill = seckillService.getSeckillById(seckillId);
		logger.info("seckill={}", seckill);
	}

	@Test
	public void testSeckillLogic() {
		long seckillId = 1000L;
		Exposer exposer = seckillService.getExposeEntity(seckillId);
		if(exposer.isExpose()) {
			logger.info("exposer={}",exposer);
			long userPhone = 18811123333L;
			String md5String = exposer.getMd5String();
			try {
				Excution excution = seckillService.excuteSeckill(seckillId, userPhone, md5String);
				logger.info("excution={}",excution);
			} catch (RepeatKillException e1) {
				logger.error(e1.getMessage());
			}catch (SeckillClosedException e2) {
				logger.error(e2.getMessage());
			}catch (SeckillException e) {
				logger.error(e.getMessage());
			}
		}else {
			logger.info("exposer={}",exposer);
		}	
	}
}
