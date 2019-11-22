package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReRecertificacionEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReRecertificacion {

	@Autowired
	private ReRecertificacionDAO recertificacionDAO;
	
	@Test
	public void findByEstatusRecertificacion() {
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByEstatus(true);
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findByIdJefeandIdPeriodo() {
		List<ReRecertificacionEntity> lista = recertificacionDAO.findByIdRecertificacionPeriodo("0219");
		log.info("Lista: " + lista);
	}
	
}
