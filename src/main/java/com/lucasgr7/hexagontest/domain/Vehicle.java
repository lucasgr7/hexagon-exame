package com.lucasgr7.hexagontest.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="VEHICLE")
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1281996955136816880L;
	
	public Vehicle() {}
	
	public Vehicle(String name, String desc, Integer vehicle_type, String plate) {
		super();
		this.name = name;
		this.desc = desc;
		this.vehicleType = vehicle_type;
		this.plate = plate;
	}

	
	public Vehicle(int id, String name, String desc, Integer vehicle_type, String plate) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.vehicleType = vehicle_type;
		this.plate = plate;
	}

	@Id
	@Column(name="ID", updatable=false, nullable=false)
	@GeneratedValue
	public int id;

	@Column(name="NAME")
	public String name;

	@Column(name="DESCRIPTION")
	public String desc;

	@Column(name="VEHICLE_TYPE")
	public Integer vehicleType;

	@Column(name="PLATE")	
	public String plate;

}
