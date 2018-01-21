package com.lucasgr7.hexagontest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import com.lucasgr7.hexagontest.controller.contract.IVehicleController;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSearchVehicleResponse;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleRequest;
import com.lucasgr7.hexagontest.domain.Vehicle;
import com.lucasgr7.hexagontest.domain.VehicleType;
import com.lucasgr7.hexagontest.repo.IVehicleRepo;
import com.lucasgr7.hexagontest.repo.IVehicleTypeRepo;
import com.lucasgr7.hexagontest.util.DtoResponseBase;


@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ComponentScan({"com.lucasgr7.hexagontest"})
@DataJpaTest
public class CrudVehicleTest {
	
	@Autowired
	private IVehicleController controller;
	
	@Autowired
	private IVehicleRepo repo;
	
	@Autowired 
	private IVehicleTypeRepo typeRepo;
	
	private Integer VehicleId = 0;
	
	@Before
	public void init() {
		typeRepo.save(new VehicleType(EnumVehicleTypeName.savedTypeName, EnumVehicleTypeDesc.savedTYpeDesc));
		VehicleId = typeRepo.findAll().get(0).id;
		repo.save(new Vehicle(EnumName.salvo1, EnumDesc.salvo1, VehicleId, "123-ABCD"));
	}
	
	@Test
	public void TestSave() {
		long countRegistros = repo.count();
		DtoResponseBase response = (DtoResponseBase) controller.save(new DtoSaveVehicleRequest(EnumName.novo1, 
				EnumDesc.novo1, 
				VehicleId, 
				"ABC-1234")).getBody();
				
		assertTrue(!response.ExistemErros());
		assertTrue(repo.count() == countRegistros + 1);
		
		Vehicle v = repo.findOneByName(EnumName.novo1);
		assertNotNull(v);
		assertTrue(v.desc.equals(EnumDesc.novo1));
	}

	@Test
	public void TestUpdate() {
		long countRegistros = repo.count();
		
		Vehicle registroSalvo = repo.findOneByName(EnumName.salvo1);
		DtoResponseBase response = (DtoResponseBase) controller.update(new DtoUpdateVehicleRequest(registroSalvo.id, 
				EnumName.salvoAlteracao1, 
				registroSalvo.desc, 
				registroSalvo.vehicleType, 
				"AAAA")).getBody();
		
		assertTrue(!response.ExistemErros());
		assertTrue(countRegistros == repo.count());
		Vehicle registroAlterado = repo.findOne(registroSalvo.id);
		assertTrue(registroAlterado.name.equals(EnumName.salvoAlteracao1));
		assertTrue(registroAlterado.plate.equals("AAAA"));
	}


	@Test
	public void TestSelect() {
		DtoSearchVehicleResponse response = (DtoSearchVehicleResponse) controller.search(null, EnumName.salvo1, null, null).getBody();
		assertNotNull(response);
		assertTrue(!response.ExistemErros());
		assertNotNull(response.vehicles);
		assertTrue(!response.vehicles.isEmpty());
		assertTrue(response.vehicles.get(0).name.equals(EnumName.salvo1));
	}

	@Test
	public void TestDelete() {
		long countRegistros = repo.count();
		
		Integer idDeletado = repo.findAll().get(0).id;
		
		DtoResponseBase response = (DtoResponseBase) controller.delete(idDeletado).getBody();
		assertTrue(!response.ExistemErros());
		assertTrue(countRegistros - 1 == repo.count());		
	}
	
	public static class EnumName{
		public static String novo1 = "namenovo1";
		public static String salvo1 = "salvo1";
		public static String salvoAlteracao1 = "aaaaalvo1";
	}
	public static class EnumDesc{
		public static String novo1 = "novodesc";
		public static String salvo1 = "salvo1desc";
	}
	public static class EnumVehicleTypeName{
		public static String savedTypeName = "t123";
	}
	public static class EnumVehicleTypeDesc{
		public static String savedTYpeDesc = "d123";
	}
}
