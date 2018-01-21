package com.lucasgr7.hexagontest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasgr7.hexagontest.domain.VehicleType;

public interface IVehicleTypeRepo extends JpaRepository<VehicleType, Integer>{

	VehicleType findOneByName(String name1);

	List<VehicleType> findAllById(Integer id);

	List<VehicleType> findAllByName(String name);

}
