package com.test.view.rest.presentation;

import java.io.Serializable;

public class SubscriptionResponse extends BaseRepresentation implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7210148905028442052L;
	private String accessKey;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

}
