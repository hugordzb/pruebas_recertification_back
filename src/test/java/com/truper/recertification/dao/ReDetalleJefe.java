package com.truper.recertification.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReDetalleJefeEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReDetalleJefe {

	@Autowired
	private ReDetalleJefeDAO detalleDAO;
	
	@Test
	public void findByIdDepartamento() {
		ReDetalleJefeEntity lista = detalleDAO.findByIdDepartamento(1);
		log.info("Lista: " + lista);
	}

	@Test
	public void findByIdJefe() {
		ReDetalleJefeEntity lista = detalleDAO.findById("jefe").get();
		log.info("Lista: " + lista);
	}
}
