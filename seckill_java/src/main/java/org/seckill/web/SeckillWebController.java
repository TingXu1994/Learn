package org.seckill.web;

import java.util.Date;
import java.util.List;

import org.seckill.dto.Excution;
import org.seckill.dto.Exposer;
import org.seckill.dto.ResponseResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillClosedException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SeckillWebController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String List(Model model) {
		// TODO:这里的数值是给定的。
		List<Seckill> seckills = seckillService.getAllSeckills(0, 5);
		logger.info("List={}", seckills);
		model.addAttribute("list", seckills);
		// list.jsp + model equals ModelAndView,这里由配置中/WEB-INF/jsp/list.jsp
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String exposeSeckillLink(@PathVariable("seckillId") Long seckillId, Model model) {
		// TODO:forward和redirect的区别
		if (seckillId == null)
			return "redirect:/list";
		Seckill seckill = seckillService.getSeckillById(seckillId);
		if (seckill == null)
			return "forward:/list";
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public ResponseResult<Long> getServerTime() {
		Date now = new Date();
		return new ResponseResult<Long>(true, now.getTime());
	}

	// TODO: get post的区别
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public ResponseResult<Exposer> getExposer(@PathVariable("seckillId") Long seckillId) {
		try {
			Exposer exposer = seckillService.getExposeEntity(seckillId);
			//logger.info(exposer.toString());
			return new ResponseResult<>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ResponseResult<>(false, e.getMessage());
		}
	}

	@RequestMapping(value = "/{seckillId}/excution/{md5String}", method = RequestMethod.POST, produces = {
			"application/json; charset=utf-8" })
	@ResponseBody
	public ResponseResult<Excution> excute(@PathVariable("seckillId") long seckillId,
			@PathVariable("md5String") String md5String,
			@CookieValue(value = "userPhone", required = false) long userPhone) {
		logger.info("excution");
		try {
			Excution excution = seckillService.excuteSeckill(seckillId, userPhone, md5String);
			return new ResponseResult<>(true, excution);
		} catch (RepeatKillException e) {
			Excution excution = new Excution(seckillId, SeckillStateEnum.REPEAT_KILL);
			return new ResponseResult<>(true, excution);
		} catch (SeckillClosedException e) {
			Excution excution = new Excution(seckillId, SeckillStateEnum.END);
			return new ResponseResult<>(true, excution);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Excution excution = new Excution(seckillId, SeckillStateEnum.INNER_ERROR);
			return new ResponseResult<>(true, excution);
		}

	}

}
