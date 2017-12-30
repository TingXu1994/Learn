package org.seckill.dto;

/**
 * 用来封装秒杀接口，解决用户通过链接访问等安全性问题；
 * BTW:删去了视频里的三个时间(now,start,end)，没什么用
 * 这里只需要提供一个暴露url(md5)就行，时间判断在Service里做
 */

public class Exposer {
	// 是否开启秒杀
	private boolean isExpose;
	// 秒杀id及MD5加密字符串
	private long seckillId;
	private String md5String;
	
	
	public Exposer(boolean isExpose, long seckillId) {
		super();
		this.isExpose = isExpose;
		this.seckillId = seckillId;
	}

	public Exposer(boolean isExpose, long seckillId, String md5String) {
		super();
		this.isExpose = isExpose;
		this.seckillId = seckillId;
		this.md5String = md5String;
	}

	public boolean isExpose() {
		return isExpose;
	}

	public void setExpose(boolean isExpose) {
		this.isExpose = isExpose;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getMd5String() {
		return md5String;
	}

	public void setMd5String(String md5String) {
		this.md5String = md5String;
	}

	@Override
	public String toString() {
		return "Exposer [isExpose=" + isExpose + ", seckillId=" + seckillId + ", md5String=" + md5String + "]";
	}


}
