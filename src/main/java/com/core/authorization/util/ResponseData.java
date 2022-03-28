package com.core.authorization.util;

public class ResponseData<T> {

	private ResponseHeader header;
	private T body;
	public ResponseData(ResponseHeader header) {
		super();
		this.header = header;
	}
	public ResponseData(ResponseHeader header, T body) {
		super();
		this.header = header;
		this.body = body;
	}
	public ResponseHeader getHeader() {
		return header;
	}
	public void setHeader(ResponseHeader header) {
		this.header = header;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}
}
