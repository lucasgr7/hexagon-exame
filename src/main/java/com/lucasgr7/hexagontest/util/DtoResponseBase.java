package com.lucasgr7.hexagontest.util;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lucasgr7.hexagontest.util.AppErrors.TipoErro;

public class DtoResponseBase  {
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public List<AppErrors.Model> errors;
	
	public DtoResponseBase() {

		if(this.errors == null || this.errors.isEmpty()) {
			this.errors = new ArrayList<AppErrors.Model>();
		}
	}

	public void AdicionarErro(TipoErro s) {
		this.errors.add(new AppErrors.Model(s));
	}
	public void AdicionarErro(List<AppErrors.Model> ss) {
		this.errors.addAll(ss);
	}

	public void AdicionarErro(TipoErro s, Exception e) {
		s.moreInfo = e.getMessage();
		this.errors.add(new AppErrors.Model(s));
	}

	public void AdicionarErro(TipoErro s, String detail) {
		s.moreInfo = detail;
		this.errors.add(new AppErrors.Model(s));
	}
	
	
	public Boolean ExistemErros() {
		return this.errors != null && !this.errors.isEmpty();
	}
	public boolean getSuccess() {
		return !ExistemErros();
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public boolean ExisteErro(TipoErro s) {
		if (this.ExistemErros()) {
			List<AppErrors.Model> Erros = this.errors.stream()
					.filter(x -> x.getErrorCode() == s.errorCode )
					.collect(Collectors.toList());
			return Erros.size() > 0;
		}
		else
			return false;
	}

}