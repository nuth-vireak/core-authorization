package com.core.authorization.util;

public class ResponseHeader {
	
	private String successYN;
	private String resultCode;
	private String resultMessage;
	private String transactionDate;
	public String getSuccessYN() {
		return successYN;
	}
	public void setSuccessYN(String successYN) {
		this.successYN = successYN;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setHeader (String successYN, String resultCode, String resultMessage, String transactionDate) {
		this.successYN = successYN;
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
		this.transactionDate = transactionDate;
	}
}
