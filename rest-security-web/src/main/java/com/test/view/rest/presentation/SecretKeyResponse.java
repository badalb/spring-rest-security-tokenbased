package com.test.view.rest.presentation;

import java.io.Serializable;

public class SecretKeyResponse extends BaseRepresentation implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6485516124535705404L;

	private String accessKey;

	private String hmacKey;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getHmacKey() {
		return hmacKey;
	}

	public void setHmacKey(String hmacKey) {
		this.hmacKey = hmacKey;
	}

}
