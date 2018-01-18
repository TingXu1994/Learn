package org.seckill.dto;

public class ResponseResult<T> {

	private boolean flag;
	private T data;
	private String error;

	public ResponseResult(boolean flag, T data) {
		super();
		this.flag = flag;
		this.data = data;
	}

	public ResponseResult(boolean flag, String error) {
		super();
		this.flag = flag;
		this.error = error;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "WebResult [flag=" + flag + ", data=" + data + ", error=" + error + "]";
	}

}
