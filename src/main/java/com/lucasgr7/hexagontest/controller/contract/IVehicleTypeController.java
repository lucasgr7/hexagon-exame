package com.lucasgr7.hexagontest.controller.contract;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleTypeRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleTypeRequest;

@Component
public interface IVehicleTypeController {
	public ResponseEntity<?> save(DtoSaveVehicleTypeRequest dto);
	
	public ResponseEntity<?> update(DtoUpdateVehicleTypeRequest dto);
	
	public ResponseEntity<?> delete(Integer id);
	
	public ResponseEntity<?> search(Integer id, String name);
}
