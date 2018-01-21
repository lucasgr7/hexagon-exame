package com.lucasgr7.hexagontest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucasgr7.hexagontest.controller.contract.IVehicleTypeController;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleTypeRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleTypeRequest;
import com.lucasgr7.hexagontest.util.AppErrors;
import com.lucasgr7.hexagontest.util.DtoResponseBase;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@DataJpaTest
public class ErrorVehicleTypeTest {
	

	@Autowired
	private IVehicleTypeController controller;
	
	@Test
	public void TestSaveNullName() {

		DtoResponseBase response = (DtoResponseBase) controller.save(new DtoSaveVehicleTypeRequest(null, EnumDesc.desc1)).getBody();
		
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.VehicleTypeNameInvalido));
	}	
	@Test
	public void TestUpdateNullId() {

		DtoResponseBase response = (DtoResponseBase) controller.update(new DtoUpdateVehicleTypeRequest(null, EnumName.name1, EnumDesc.desc1)).getBody();
		
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.VehicleTypeIdInvalido));
	}
	@Test
	public void TestUpdateNullName() {

		DtoResponseBase response = (DtoResponseBase) controller.update(new DtoUpdateVehicleTypeRequest(EnumId.savedId, null, EnumDesc.desc1)).getBody();
		
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.VehicleTypeNameInvalido));
	}
	@Test
	public void TestDeleteNotFound() {
		DtoResponseBase response = (DtoResponseBase) controller.delete(123456).getBody();
		
		assertTrue(response.ExistemErros());
		assertTrue(response.ExisteErro(AppErrors.TipoErro.VehicleTYpeIdNaoEncontrado));
	}
	
	
	public static class EnumId{
		public static Integer savedId = 1;
	}
	public static class EnumName{
		private static String name1 = "name1";
	}
	public static class EnumDesc{
		private static String desc1 = "desc1";
	}
}
