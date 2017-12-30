package org.seckill.enums;

public enum SeckillStateEnum {
	SUCCESS(1, "秒杀成功"), END(0, "秒杀结束"), REPEAT_KILL(-1, "重发秒杀"), INNER_ERROR(-2, "系统异常"), DATA_REWRITE(-3, "数据篡改");

	private int status;
	private String statusInfo;

	private SeckillStateEnum(int status, String statusInfo) {
		this.status = status;
		this.statusInfo = statusInfo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
	}

	public SeckillStateEnum indexof(int status) {
		for (SeckillStateEnum seckillStateEnum : values()) {
			if (seckillStateEnum.getStatus() == status) {
				return seckillStateEnum;
			}
		}
		return null;
	}
}
