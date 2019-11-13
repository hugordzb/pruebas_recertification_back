package com.truper.recertification.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReSistemaEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReSistema {

	@Autowired
	private ReSistemaDAO sistemaDAO;

	@Test
	public void findBySistema() {
		ReSistemaEntity lista = sistemaDAO.findBySistema("CIAT");
		log.info("Lista: " + lista);
	}
	
}
