package com.core.authorization.util;

public class RequestData<T> {
	
	private T body;

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "RequestData [body=" + body + "]";
	}

}
