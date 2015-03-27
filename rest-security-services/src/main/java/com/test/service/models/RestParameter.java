package com.test.service.models;

import java.io.Serializable;

public class RestParameter implements Comparable<RestParameter>, Serializable{

	private static final long serialVersionUID = -8654122030780643503L;
	
	private String paramName;
	private String paramValue;
	private String order;
	
	public RestParameter(){}
	
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}


	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public int compareTo(RestParameter arg0) {
		return Integer.parseInt(this.order) - Integer.parseInt(arg0.getOrder());
	}

	@Override
	public String toString() {
		return "RestParameter [paramName=" + paramName + ", paramValue="
				+ paramValue + ", order=" + order + "]";
	}
	
}
