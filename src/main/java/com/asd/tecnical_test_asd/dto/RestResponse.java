package com.asd.tecnical_test_asd.dto;

public class RestResponse {

	private String message;
	private Object data;
	private int status;


	public RestResponse() {

	}


	public RestResponse(String message) {
		super();
		this.message = message;
	}

	public RestResponse(int status,String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


}
