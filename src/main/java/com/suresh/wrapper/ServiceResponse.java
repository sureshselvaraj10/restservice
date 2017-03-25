package com.suresh.wrapper;

/**
 * pojo for forming service response
 */
public class ServiceResponse {
	private Object payload;
	private String error;

	public String getError() {

		return error;
	}

	public void setError(String error) {

		this.error = error;
	}

	public Object getPayload() {

		return payload;
	}

	public void setPayload(Object payload) {

		this.payload = payload;
	}

}
