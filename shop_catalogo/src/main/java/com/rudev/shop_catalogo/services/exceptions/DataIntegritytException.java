package com.rudev.shop_catalogo.services.exceptions;

public class DataIntegritytException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public DataIntegritytException(String msg) {
		super(msg);
	}

}
