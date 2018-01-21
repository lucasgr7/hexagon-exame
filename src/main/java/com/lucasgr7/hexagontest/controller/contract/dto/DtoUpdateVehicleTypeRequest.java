package com.lucasgr7.hexagontest.controller.contract.dto;

public class DtoUpdateVehicleTypeRequest {
	
	public DtoUpdateVehicleTypeRequest() {}
	
	public DtoUpdateVehicleTypeRequest(Integer id, String name, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	public Integer id;
	public String name;
	public String desc;
}
