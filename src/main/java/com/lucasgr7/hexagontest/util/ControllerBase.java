package com.lucasgr7.hexagontest.util;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public abstract class ControllerBase {
	
	/**
	 * Gera uma resposta em Json passando o objeto de Response genérico, capturando o HttpStatus necessário
	 * @param response DTO de resposta do método da controller
	 * @return ResponseEntity para o client
	 */
	protected <T> ResponseEntity<?> CreateResponse(T response){
		DtoResponseBase responseErro = (DtoResponseBase) response;	
		if(responseErro.ExistemErros()) {
			return new ResponseEntity<T>(response, responseErro.errors.get(0).getStatus());	
		}else {
			return new ResponseEntity<T>(response, HttpStatus.OK);	
		}
	}

	protected Boolean isValidString(String s) {
		return s != null && !s.isEmpty();
	}
	/**
	 * Valida se tem algum item na lista
	 * @param l Lista de elementos
	 * @return True caso a lista possua qualquer elemento
	 */
	protected Boolean hasAny(List<?> l) {
		return l != null && !l.isEmpty();
	}
	
}