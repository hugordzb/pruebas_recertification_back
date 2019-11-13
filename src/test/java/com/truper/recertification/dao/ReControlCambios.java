package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReControlCambiosEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReControlCambios {

	@Autowired
	private ReControlCambiosDAO controlDAO;
	
	@Test
	public void findByAtendio() {
		List<ReControlCambiosEntity> lista = controlDAO.findByAtendio("atendio");
		log.info("Lista: " + lista.size());
	}

	@Test
	public void findByEstatus() {
		List<ReControlCambiosEntity> lista = controlDAO.findByEstatus(false);
		log.info("Lista: " + lista.size());
	}
}
