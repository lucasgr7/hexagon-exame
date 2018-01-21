package com.lucasgr7.hexagontest.util;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AppErrors {
	public enum TipoErro {

		DtoNulo(1, "Missing request body", HttpStatus.BAD_REQUEST), 
		VehicleNameInvalido(2, "Name invalido", HttpStatus.BAD_REQUEST), 
		VehiclePlateInvalido(3, "Plate invalido", HttpStatus.BAD_REQUEST), 
		VehicleTypeInvalido(4, "Vehicle Type invalido ou não existe", HttpStatus.BAD_REQUEST), 
		IdInvalido(5, "Vehicle ID não encontrado" , HttpStatus.NOT_FOUND), 
		VehicleTypeNameInvalido(6, "Vehicle Type inválido", HttpStatus.BAD_REQUEST), 
		VehicleTypeIdInvalido(7, "Vehicle Type ID é inválido", HttpStatus.BAD_REQUEST), 
		VehicleTYpeIdNaoEncontrado(8, "Vehicle Type ID não encontrado" , HttpStatus.NOT_FOUND), 
		ErroInesperado(9, "Desculpe, aconteceu um impresvisto, erro 500", HttpStatus.INTERNAL_SERVER_ERROR), 
		VehicleNotFound(10, "Vehicle não encontrado", HttpStatus.NOT_FOUND);

		
		public Integer errorCode;
		public String message;		
		public String moreInfo;
		public HttpStatus statusCode;

		

		TipoErro(final Integer errorCode, final String message, final String moreInfo, HttpStatus status) {

	        this.errorCode = errorCode;
	        this.message = message;
	        this.moreInfo = moreInfo;
	        this.statusCode = status;
	    }
		
		TipoErro(final Integer errorCode, final String message, HttpStatus status) {

	        this.errorCode = errorCode;
	        this.message = message;
	        this.statusCode = status;
	    }
		
		
		
		
	
	}

	

	public static class Model implements Serializable {		
		private static final long serialVersionUID = -3618471109420697733L;		

		public Model() {}
		
		public Model(AppErrors.TipoErro t) {
			this.message = t.message;
			this.errorCode = t.errorCode;
			this.status = t.statusCode;
			this.moreInfo = t.moreInfo;
		}
		
		public Model(Integer errorCode, String message,  String moreInfo, HttpStatus s) {
			super();
			this.message = message;
			this.errorCode = errorCode;
			this.moreInfo = moreInfo;
			this.status = s;
		}
		
		public Model(Integer errorCode, String message,  HttpStatus s) {
			super();
			this.message = message;
			this.errorCode = errorCode;
			this.status = s;
		}

		@JsonProperty("message")
		private String message;

		
		@JsonProperty("errorCOde")
		private Integer errorCode;
		
		@JsonProperty("moreInfo")
		private String moreInfo;
		
		private HttpStatus status;

		public String getMessage() {
			return message;
		}

		public Integer getErrorCode() {
			return errorCode;
		}

		public String getMoreInfo() {
			return moreInfo;
		}

		public HttpStatus getStatus() {
			return status;
		}

		
		
	}

	
}