package com.truper.recertification.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReDepartamentoEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReDepartamento {

	@Autowired
	private ReDepartamentoDAO departamentoDAO;
	
	@Test
	public void findByDepartamento() {
		ReDepartamentoEntity lista = departamentoDAO.findByDepartamento("Departamento");
		log.info("Lista: " + lista);
	}
	
}
