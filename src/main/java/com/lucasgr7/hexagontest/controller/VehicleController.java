package com.lucasgr7.hexagontest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucasgr7.hexagontest.controller.contract.IVehicleController;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSearchVehicleResponse;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleRequest;
import com.lucasgr7.hexagontest.domain.Vehicle;
import com.lucasgr7.hexagontest.domain.VehicleType;
import com.lucasgr7.hexagontest.repo.IVehicleRepo;
import com.lucasgr7.hexagontest.repo.IVehicleTypeRepo;
import com.lucasgr7.hexagontest.util.AppErrors;
import com.lucasgr7.hexagontest.util.ControllerBase;
import com.lucasgr7.hexagontest.util.DtoResponseBase;

@Controller
@RequestMapping(value = "vehicle")
public class VehicleController extends ControllerBase implements IVehicleController{
	
	@Autowired
	private IVehicleTypeRepo vehicleTypeRepo;
	
	@Autowired
	private IVehicleRepo vehicleRepo;

	@Override
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody DtoSaveVehicleRequest dto) {
		DtoResponseBase response = new DtoResponseBase();
		
		if(dto == null) {
			response.AdicionarErro(AppErrors.TipoErro.DtoNulo);
			return CreateResponse(response);
		}
		if(!isValidString(dto.name))
			response.AdicionarErro(AppErrors.TipoErro.VehicleNameInvalido);
		if(!isValidString(dto.plate))
			response.AdicionarErro(AppErrors.TipoErro.VehiclePlateInvalido);
		if(dto.vehicleType == null || !vehicleTypeRepo.exists(dto.vehicleType))
			response.AdicionarErro(AppErrors.TipoErro.VehicleTypeInvalido);
		
		if(response.ExistemErros()) {
			return CreateResponse(response);
		}
		try {
			vehicleRepo.save(new Vehicle(dto.name, dto.desc, dto.vehicleType, dto.plate));
		}catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
			
		
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "", method = RequestMethod.PATCH)
	public ResponseEntity<?> update(DtoUpdateVehicleRequest dto) {
		DtoResponseBase response = new DtoResponseBase();
		
		if(dto == null) {
			response.AdicionarErro(AppErrors.TipoErro.DtoNulo);
			return CreateResponse(response);
		}
		if(!isValidString(dto.name))
			response.AdicionarErro(AppErrors.TipoErro.VehicleNameInvalido);
		if(!isValidString(dto.plate))
			response.AdicionarErro(AppErrors.TipoErro.VehiclePlateInvalido);
		if(dto.vehicleType == null && vehicleTypeRepo.exists(dto.vehicleType))
			response.AdicionarErro(AppErrors.TipoErro.VehicleTypeInvalido);
		if(dto.id == null || !vehicleRepo.exists(dto.id)) 
			response.AdicionarErro(AppErrors.TipoErro.IdInvalido);
		
		if(response.ExistemErros()) {
			return CreateResponse(response);
		}
		try {
			Vehicle v = vehicleRepo.findOne(dto.id);
			if(v == null) {
				response.AdicionarErro(AppErrors.TipoErro.VehicleNotFound);
				return CreateResponse(response);
			}
			v.name = dto.name;
			v.desc = dto.desc;
			v.vehicleType = dto.vehicleType;
			v.plate = dto.plate;
			vehicleRepo.save(v);	
		}
		catch(Exception ex){
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
			
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
		DtoResponseBase response = new DtoResponseBase();
		if(id == null) {
			response.AdicionarErro(AppErrors.TipoErro.IdInvalido);
			return CreateResponse(response);
		}
		try {
			vehicleRepo.delete(id);	
		}
		catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam(name="id", required=false) Integer id, @RequestParam(name="name", required=false) String name, 
			@RequestParam(name="plate", required=false) String plate, @RequestParam(name="vehicleType", required=false)  Integer vehicleType) {
		DtoSearchVehicleResponse response = new DtoSearchVehicleResponse();
		
		List<Vehicle> veiculos = null;
		try {

			//Filtro por Id
			if(id != null) {
				veiculos = vehicleRepo.findAllById(id);
			}
			
			//Filtro por name
			if(isValidString(name)) {
				if(veiculos == null) {
					veiculos = vehicleRepo.findAllByName(name);	
				}else if(hasAny(veiculos)) {
					veiculos = veiculos.stream().filter(x -> x.name.contains(name)).collect(Collectors.toList());
				}
			}
			
			//Filtro por plate
			if(isValidString(plate)) {
				if(veiculos == null) {
					veiculos = vehicleRepo.findAllByPlate(plate);	
				}else if(hasAny(veiculos)) {
					veiculos = veiculos.stream().filter(x -> x.plate.contains(plate)).collect(Collectors.toList());	
				}
			}
			
			//Filtro por VehicleType
			if(vehicleType != null) {
				if(veiculos == null) {
					veiculos = vehicleRepo.findAllByVehicleType(vehicleType);	
				}else if(hasAny(veiculos)) {
					veiculos = veiculos.stream().filter(x -> x.vehicleType.equals(vehicleType)).collect(Collectors.toList());
				}
			}
			
			if(hasAny(veiculos)) {
				response.vehicles = new ArrayList<DtoSearchVehicleResponse.Vehicle>();
				for(Vehicle v : veiculos) {
					if(v.name != null && v.vehicleType != null) {
						VehicleType vt = vehicleTypeRepo.findOne(v.vehicleType);
						response.vehicles.add(new DtoSearchVehicleResponse.Vehicle(v.id, v.name, v.desc, v.plate, 
								new DtoSearchVehicleResponse.VehicleType(vt.id, vt.name, vt.description)));	
					}
				}
			}else {
				response.vehicles = new ArrayList<com.lucasgr7.hexagontest.controller.contract.dto.DtoSearchVehicleResponse.Vehicle>();
			}
		}catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
		
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public ResponseEntity<?> all() {
		DtoSearchVehicleResponse response = new DtoSearchVehicleResponse();
		try {
			List<Vehicle> veiculos = vehicleRepo.findAll();
			if(hasAny(veiculos)){
				for(Vehicle v : veiculos) {
					if(v.name != null && v.vehicleType != null) {
						VehicleType vt = vehicleTypeRepo.findOne(v.vehicleType);
						response.vehicles.add(new DtoSearchVehicleResponse.Vehicle(v.id, v.name, v.desc, v.plate, 
								new DtoSearchVehicleResponse.VehicleType(vt.id, vt.name, vt.description)));	
					}
				}
			}
		}
		catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
		return CreateResponse(response);
	}
	
}
