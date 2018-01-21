package com.lucasgr7.hexagontest.controller.contract.dto;


public class DtoSaveVehicleRequest {
	
	public DtoSaveVehicleRequest() {
		
	}
	public DtoSaveVehicleRequest(String name, String desc, Integer vehicleType, String plate) {
		super();
		this.name = name;
		this.desc = desc;
		this.vehicleType = vehicleType;
		this.plate = plate;
	}
	public String name;
	public String desc;
	public Integer vehicleType;
	public String plate;
	
}
