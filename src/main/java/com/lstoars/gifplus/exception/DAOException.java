package com.lstoars.gifplus.exception;

public class DAOException extends Exception{
	
	private static final long serialVersionUID = -7408135210802175678L;

	public DAOException() {
		super();
	}

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}

	public DAOException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
