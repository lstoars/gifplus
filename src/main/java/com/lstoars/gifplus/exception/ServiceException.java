package com.lstoars.gifplus.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 7464744855019194698L;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
