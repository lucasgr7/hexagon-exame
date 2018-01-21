package com.lucasgr7.hexagontest.util;
import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TipoErro implements Serializable{

	private static final long serialVersionUID = -3618471109420697733L;
	
    @JsonProperty("errorCode")
    protected Integer errorCode;
    
    @JsonProperty("developerMessage")
	protected String developerMessage;
    
    @JsonProperty("userMessage")
    protected String userMessage;
    
    @JsonProperty("moreInfo")
    protected String moreInfo;
    
    protected HttpStatus httpstatus;
    
    @JsonIgnore
    protected Integer key;
        
	public TipoErro() {
			
	}
	
	public TipoErro(String developerMessage, String userMessage, Integer errorCode, String moreInfo, HttpStatus httpstatus, int key) {
		super();
		this.developerMessage = developerMessage;
		this.userMessage = userMessage;
		this.errorCode = errorCode;
		this.moreInfo = moreInfo;
		this.httpstatus = httpstatus;
		this.key = key;
	}

	public Integer getErrorCode() {
		return errorCode;
	}



	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}



	public String getDeveloperMessage() {
		return developerMessage;
	}



	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}



	public String getUserMessage() {
		return userMessage;
	}



	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}



	public String getMoreInfo() {
		return moreInfo;
	}



	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}



	public HttpStatus getHttpstatus() {
		return httpstatus;
	}



	public void setHttpstatus(HttpStatus httpstatus) {
		this.httpstatus = httpstatus;
	}



	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
    
}
