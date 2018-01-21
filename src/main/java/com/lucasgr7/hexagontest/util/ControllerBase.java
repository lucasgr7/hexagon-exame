package com.lucasgr7.hexagontest.util;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lucasgr7.hexagontest.domain.VehicleType;

public abstract class ControllerBase {
	
	
	protected <T> ResponseEntity<?> CreateResponse(T response){
		DtoResponseBase responseErro = (DtoResponseBase) response;	
		if(responseErro.ExistemErros()) {
			return new ResponseEntity<T>(response, responseErro.errors.get(0).getStatus());	
		}else {
			return new ResponseEntity<T>(response, HttpStatus.OK);	
		}
	}
	protected <T> ResponseEntity<?> CreateResponse(T response, HttpStatus status){
		return new ResponseEntity<T>(response, status);
	}

	protected Boolean isValidString(String s) {
		return s != null && !s.isEmpty();
	}
	protected Boolean hasAny(List<?> l) {
		return l != null && !l.isEmpty();
	}
	
}