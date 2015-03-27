package com.test.security.config;

public final class RestCredentials {

    private String requestParams;
    private String hashSignature;

    public RestCredentials(String requestParams, String hashSignature) {
        this.requestParams = requestParams;
        this.hashSignature = hashSignature;
    }

	public String getRequestParams() {
		return requestParams;
	}

	public String getHashSignature() {
		return hashSignature;
	}

}