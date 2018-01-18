package org.seckill.exception;

public class SeckillClosedException extends SeckillException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	public SeckillClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillClosedException(String message) {
		super(message);
	}

}
