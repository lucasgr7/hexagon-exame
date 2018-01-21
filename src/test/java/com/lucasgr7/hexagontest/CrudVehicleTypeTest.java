package com.lucasgr7.hexagontest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucasgr7.hexagontest.controller.contract.IVehicleTypeController;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSaveVehicleTypeRequest;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoSearchVehicleTypeResponse;
import com.lucasgr7.hexagontest.controller.contract.dto.DtoUpdateVehicleTypeRequest;
import com.lucasgr7.hexagontest.domain.VehicleType;
import com.lucasgr7.hexagontest.repo.IVehicleTypeRepo;
import com.lucasgr7.hexagontest.util.DtoResponseBase;

@RunWith(SpringRunner.class)
@TestPropertySource("classpath:test.properties")
@ComponentScan({"com.lucasgr7.hexagontest"})
@DataJpaTest
public class CrudVehicleTypeTest {

	@Autowired
	private IVehicleTypeController controller;
	
	@Autowired
	private IVehicleTypeRepo repo;
	
	@Before
	public void init() {
		repo.save(new VehicleType(EnumName.savedName, EnumDesc.savedDesc));
	}
	
	@Test
	public void TestSearch() {
		Integer id = repo.findAll().get(0).id;
		DtoSearchVehicleTypeResponse response = (DtoSearchVehicleTypeResponse) controller.search(id, null).getBody();
		
		assertNotNull(response);
		assertTrue(response.vehiclesTypes.size() > 0);
		assertTrue(response.vehiclesTypes.stream().findAny().get().id > 0);
	}
	
	@Test
	public void TestSave() {
		long countRegistros = repo.count();
		
		DtoResponseBase response = (DtoResponseBase) controller.save(new DtoSaveVehicleTypeRequest(EnumName.name1, EnumDesc.desc1)).getBody();
		
		assertTrue(!response.ExistemErros());
		assertTrue(repo.count() == countRegistros + 1);
		
		VehicleType registro = repo.findOneByName(EnumName.name1);
		assertNotNull(registro);
		assertTrue(registro.description.equals(EnumDesc.desc1));
	}
	
	@Test
	public void TestUpdate() {
		long countRegistros = repo.count();

		Integer id = repo.findAll().get(0).id;
		DtoResponseBase response = (DtoResponseBase) controller.update(new DtoUpdateVehicleTypeRequest(id, EnumName.changeName, EnumDesc.savedDesc)).getBody();
		assertTrue(!response.ExistemErros());
		
		VehicleType registro = repo.findOne(id);
		assertTrue(repo.count() == countRegistros);
		assertNotNull(registro);
		assertTrue(registro.description.equals(EnumDesc.savedDesc));
		assertTrue(registro.name.equals(EnumName.changeName));
	}
	
	@Test
	public void TestDelete() {
		long countRegistros = repo.count();
		
		Integer idDeletado = repo.findAll().get(0).id;
		
		DtoResponseBase response = (DtoResponseBase) controller.delete(idDeletado).getBody();
		
		assertTrue(!response.ExistemErros());
		assertTrue(repo.count() == countRegistros -1);
	}
	
	
	
	public static class EnumName{
		private static String name1 = "name1";
		private static String savedName = "name2";
		private static String changeName = "cName";
	}
	public static class EnumDesc{
		private static String desc1 = "desc1";
		private static String savedDesc = "desc2";
	}

}
