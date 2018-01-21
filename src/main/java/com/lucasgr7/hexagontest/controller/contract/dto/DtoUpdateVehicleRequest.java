package com.lucasgr7.hexagontest.controller.contract.dto;

public class DtoUpdateVehicleRequest {

	public Integer id;
	public String name;
	public String desc;
	public Integer vehicleType;
	public String plate;
	
	public DtoUpdateVehicleRequest() {
		
	}
	public DtoUpdateVehicleRequest(Integer id, String name, String desc, Integer vehicleType, String plate) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.vehicleType = vehicleType;
		this.plate = plate;
	}
	
	
}
