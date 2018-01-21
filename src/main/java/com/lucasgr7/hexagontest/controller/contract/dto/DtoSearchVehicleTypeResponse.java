package com.lucasgr7.hexagontest.controller.contract.dto;

import java.util.ArrayList;
import java.util.List;

import com.lucasgr7.hexagontest.util.DtoResponseBase;

public class DtoSearchVehicleTypeResponse extends DtoResponseBase {
	public DtoSearchVehicleTypeResponse() {
		this.vehiclesTypes = new ArrayList<VehicleType>();
	}
	public List<VehicleType> vehiclesTypes;
	
	public static class VehicleType{
		
		public VehicleType() {
			
		}
		public VehicleType(Integer id, String name, String desc) {
			super();
			this.id = id;
			this.name = name;
			this.desc = desc;
		}
		public Integer id;
		public String name;
		public String desc;
	}
}
