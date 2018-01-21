package com.lucasgr7.hexagontest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lucasgr7.hexagontest.controller.contract.IVehicleTypeController;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleTypeRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSearchVehicleTypeResponse;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleTypeRequest;
import com.lucasgr7.hexagontest.domain.VehicleType;
import com.lucasgr7.hexagontest.repo.IVehicleTypeRepo;
import com.lucasgr7.hexagontest.util.AppErrors;
import com.lucasgr7.hexagontest.util.ControllerBase;
import com.lucasgr7.hexagontest.util.DtoResponseBase;

@Controller
@RequestMapping(value = "vehicletype")
public class VehicleTypeController extends ControllerBase implements IVehicleTypeController {

	@Autowired
	private IVehicleTypeRepo repo;
	
	@Override
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody DtoSaveVehicleTypeRequest dto) {
		DtoResponseBase response = new DtoResponseBase();
		if(!isValidString(dto.name)) {
			response.AdicionarErro(AppErrors.TipoErro.VehicleTypeNameInvalido);			
		}
		if(response.ExistemErros()) {
			return CreateResponse(response);
		}
		try {
			repo.save(new VehicleType(dto.name, dto.desc));	
		}
		catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
		
		return CreateResponse(response);
		
	}

	@Override
	@RequestMapping(value = "", method = RequestMethod.PATCH)
	public ResponseEntity<?> update(@RequestBody DtoUpdateVehicleTypeRequest dto) {
		DtoResponseBase response = new DtoResponseBase();
		if(dto.id == null) 
			response.AdicionarErro(AppErrors.TipoErro.VehicleTypeIdInvalido);
		if(!isValidString(dto.name)) 
			response.AdicionarErro(AppErrors.TipoErro.VehicleTypeNameInvalido);
		if(response.ExistemErros())
			return CreateResponse(response);
		
		try {
			VehicleType v = repo.findOne(dto.id);
			if(v == null) {
				response.AdicionarErro(AppErrors.TipoErro.VehicleTYpeIdNaoEncontrado);
				return CreateResponse(response);
			}
			v.name = dto.name;
			v.description = dto.desc;
			repo.save(v);
		}
		catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
		
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
		DtoResponseBase response = new DtoResponseBase();
		if(id == null) {
			response.AdicionarErro(AppErrors.TipoErro.VehicleTypeIdInvalido);
			return CreateResponse(response);
		}
		if(!repo.exists(id)) {
			response.AdicionarErro(AppErrors.TipoErro.VehicleTYpeIdNaoEncontrado);
			return CreateResponse(response);
		}
		try {
			repo.delete(id);
		}
		catch(Exception ex) {
			response.AdicionarErro(AppErrors.TipoErro.ErroInesperado, ex);
		}
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam(name="id", required=false) Integer id, @RequestParam(name="name", required=false) String name) {
		DtoSearchVehicleTypeResponse response = new DtoSearchVehicleTypeResponse();
		List<VehicleType> tiposVeiculos = null;
		
		if(id != null) {
			tiposVeiculos = repo.findAllById(id);
		}
		
		if(isValidString(name)) {
			if( tiposVeiculos == null) {
				tiposVeiculos = repo.findAllByName(name);	
			}else if(hasAny(tiposVeiculos)) {
				tiposVeiculos = tiposVeiculos.stream().filter(x -> x.name.contains(name)).collect(Collectors.toList());
			}
		}
		
		if(hasAny(tiposVeiculos)) {
			response.vehiclesTypes = tiposVeiculos.stream().map(x -> 
			new DtoSearchVehicleTypeResponse.VehicleType(x.id, x.name, x.description)).collect(Collectors.toList());
		}
		// TODO Auto-generated method stub
		return CreateResponse(response);
	}

	@Override
	@RequestMapping(value = "all", method = RequestMethod.GET)
	public ResponseEntity<?> all() {
		DtoSearchVehicleTypeResponse response = new DtoSearchVehicleTypeResponse();
		List<VehicleType> tiposVeiculos = repo.findAll();
		
		if(hasAny(tiposVeiculos)) {
			response.vehiclesTypes = tiposVeiculos.stream().map(x -> 
			new DtoSearchVehicleTypeResponse.VehicleType(x.id, x.name, x.description)).collect(Collectors.toList());
		}
		
		return CreateResponse(response);
	}
	
}
