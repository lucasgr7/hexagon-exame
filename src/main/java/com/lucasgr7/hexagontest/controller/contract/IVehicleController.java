package com.lucasgr7.hexagontest.controller.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleRequest;

@Component
public interface IVehicleController {
	public  ResponseEntity<?> save(DtoSaveVehicleRequest dto);
	public  ResponseEntity<?> update(DtoUpdateVehicleRequest dto);
	public  ResponseEntity<?> delete(Integer id);
	public  ResponseEntity<?> search(Integer id, String name, String plate, Integer VehicleType);
}
