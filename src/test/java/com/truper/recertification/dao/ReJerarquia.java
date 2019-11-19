package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReJerarquiaEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReJerarquia {
	
	@Autowired
	private ReJerarquiaDAO jerarquiaDAO;
	
	@Test
	public void findByIdEmpleadoJefeIdJefe() {
		List<ReJerarquiaEntity> lista = jerarquiaDAO.findByIdEmpleadoJefeIdJefe("jefe");
		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByIdEmpleadoJefeIdUsuario() {
		ReJerarquiaEntity lista = jerarquiaDAO.findByIdEmpleadoJefeIdUsuario("mgmolinae");
		log.info("Lista: " + lista);
	}
	

}
