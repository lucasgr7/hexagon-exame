package com.lucasgr7.hexagontest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasgr7.hexagontest.domain.Vehicle;

public interface IVehicleRepo extends JpaRepository<Vehicle, Integer> {

	List<Vehicle> findAllByName(String name);

	List<Vehicle> findAllById(Integer id);

	List<Vehicle> findAllByPlate(String name);

	List<Vehicle> findAllByVehicleType(Integer vehicleType);

	Vehicle findOneByName(String novo1);

}
