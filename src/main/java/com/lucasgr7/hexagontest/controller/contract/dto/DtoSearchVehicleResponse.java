package com.lucasgr7.hexagontest.controller.contract.dto;

import java.util.ArrayList;
import java.util.List;

import com.lucasgr7.hexagontest.util.DtoResponseBase;

public class DtoSearchVehicleResponse extends DtoResponseBase{
	public DtoSearchVehicleResponse() {
		this.vehicles = new ArrayList<Vehicle>();
	}
	public List<Vehicle> vehicles;
	
	public static class Vehicle{
		
		public Vehicle() {
			
		}
		public Vehicle(Integer id, String name, String desc, String plate, VehicleType type) {
			super();
			this.id = id;
			this.name = name;
			this.desc = desc;
			this.plate = plate;
			this.type = type;
		}
		public Integer id;
		public String name;
		public String desc;
		public String plate;
		public VehicleType type;
	}
	
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
