package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 返回秒杀结果的dto; 参数秒杀商品id，秒杀状态，执行秒杀的用户(通过successkilled表包含用户对象);
 * 枚举类型传输状态码，eg：1/传输成功;
 */
public class Excution {
	// 秒杀商品
	private long seckillId;
	// 秒杀执行状态
	private int stateID;
	private String stateInfo;
	//
	private SuccessKilled successKilled;

	public Excution(long seckillId, SeckillStateEnum seckillStateEnum) {
		super();
		this.seckillId = seckillId;
		this.stateID = seckillStateEnum.getStatus();
		this.stateInfo = seckillStateEnum.getStatusInfo();
	}

	public Excution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
		this.seckillId = seckillId;
		this.stateID = seckillStateEnum.getStatus();
		this.stateInfo = seckillStateEnum.getStatusInfo();
		this.successKilled = successKilled;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getStateID() {
		return stateID;
	}

	public void setStateID(int stateID) {
		this.stateID = stateID;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	@Override
	public String toString() {
		return "Excution [seckillId=" + seckillId + ", stateID=" + stateID + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}

}
