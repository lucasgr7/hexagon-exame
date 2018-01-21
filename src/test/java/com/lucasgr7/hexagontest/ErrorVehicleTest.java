package com.lucasgr7.hexagontest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucasgr7.hexagontest.controller.contract.IVehicleController;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSearchVehicleResponse;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleRequest;
import com.lucasgr7.hexagontest.domain.Vehicle;
import com.lucasgr7.hexagontest.domain.VehicleType;
import com.lucasgr7.hexagontest.repo.IVehicleRepo;
import com.lucasgr7.hexagontest.repo.IVehicleTypeRepo;
import com.lucasgr7.hexagontest.util.AppErrors;
import com.lucasgr7.hexagontest.util.DtoResponseBase;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ComponentScan({"com.lucasgr7.hexagontest"})
@DataJpaTest
public class ErrorVehicleTest {

	@Autowired
	private IVehicleController controller;
	
	@Autowired
	private IVehicleRepo repo;
	
	@Autowired 
	private IVehicleTypeRepo typeRepo;
	
	@Before
	public void init() {
		typeRepo.save(new VehicleType(EnumVehicleTypeName.savedTypeName, EnumVehicleTypeDesc.savedTYpeDesc));
		repo.save(new Vehicle(EnumName.salvo1, EnumDesc.salvo1, EnumVehicleId.salvo1, "123-ABCD"));
	}
	
	@Test
	public void TestSaveNullName() {
		DtoResponseBase response = (DtoResponseBase) controller.save(new DtoSaveVehicleRequest(null, EnumDesc.novo1, EnumVehicleId.salvo1, "ABC")).getBody();
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.VehicleNameInvalido));
	}
	
	@Test
	public void TestUpdateNullNameNullId() {
		DtoResponseBase response = (DtoResponseBase) controller.update(new DtoUpdateVehicleRequest(null, null, EnumDesc.novo1, EnumVehicleId.salvo1, "ABC")).getBody();
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.VehicleNameInvalido));
		assertTrue(response.ExisteErro(AppErrors.TipoErro.IdInvalido));
	}
	
	@Test
	public void TestDeleteIdNull() {
		DtoResponseBase response = (DtoResponseBase) controller.delete(null).getBody();
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.IdInvalido));
	}
	
	@Test
	public void TestSearchNull() {
		DtoSearchVehicleResponse response = (DtoSearchVehicleResponse) controller.search(null, null, null, null).getBody();
		assertTrue(!response.ExistemErros());
		assertTrue(response.vehicles.isEmpty());
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
	public static class EnumVehicleId{
		public static Integer salvo1 = 1;
	}
	public static class EnumId{
		public static Integer salvo1 = 1;
	}
	public static class EnumVehicleTypeName{
		public static String savedTypeName = "t123";
	}
	public static class EnumVehicleTypeDesc{
		public static String savedTYpeDesc = "d123";
	}
	
}
