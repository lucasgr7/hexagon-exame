package com.lucasgr7.hexagontest.controller.contract.dto;

public class DtoSaveVehicleTypeRequest {
	
	public DtoSaveVehicleTypeRequest() {
		
	}
	
	public DtoSaveVehicleTypeRequest(String name, String desc) {
		super();
		this.name = name;
		this.desc = desc;
	}
	public String name;
	public String desc;
}
