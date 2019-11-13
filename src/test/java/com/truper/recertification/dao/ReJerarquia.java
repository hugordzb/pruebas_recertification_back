package com.truper.recertification.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReJerarquia {
	
	@Autowired
	private ReJerarquiaDAO jerarquiaDAO;
	
	@Test
	public void findByIdEmpleadoJefeIdJefe() {
//		List<ReJerarquiaEntity> lista = jerarquiaDAO.findByIdJerarquiaIdJefe("jefe");
//		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByIdEmpleadoJefeIdUsuario() {
//		ReJerarquiaEntity lista = jerarquiaDAO.findByIdJerarquiaIdUsuario("usuario");
//		log.info("Lista: " + lista);
	}
	

}
