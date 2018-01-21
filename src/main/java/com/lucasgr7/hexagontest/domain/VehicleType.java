package com.lucasgr7.hexagontest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="VEHICLE")
public class VehicleType {

	@Id
	@Column(name="ID", updatable=false, nullable=false)
	@GeneratedValue
	public int id;
	
	@Column(name="NAME")
	public String name;
	
	@Column(name="DESC")
	public String description;
	
	public VehicleType() {}

	public VehicleType(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	public VehicleType(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	
}
