package org.seckill.entity;

import java.sql.Date;

public class SuccessKilled {
	private long seckillId;
	private long userPhone;
	private short State;
	private Date createTime;

	// 变通的需求：多对一（一个秒杀对应多个成功记录）
	private Seckill seckill;

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return State;
	}

	public void setState(short state) {
		State = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	@Override
	public String toString() {
		return "SucessKilled [seckillId=" + seckillId + ", userPhone=" + userPhone + ", State=" + State
				+ ", createTime=" + createTime + ", seckill=" + seckill + "]";
	}

}
