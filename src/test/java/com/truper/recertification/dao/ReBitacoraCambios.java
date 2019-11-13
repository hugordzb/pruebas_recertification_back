package com.truper.recertification.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReBitacoraCambiosEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReBitacoraCambios {

	@Autowired
	private ReBitacoraCambiosDAO bitacoraDAO;
		
	@Test
	public void findByTipoMov() {
		List<ReBitacoraCambiosEntity> lista = bitacoraDAO.findByTipoMov("A");
		log.info("Lista: " + lista.size());
	}
	
	@Test
	public void findBySolicitante() {
		List<ReBitacoraCambiosEntity> lista = bitacoraDAO.findBySolicitante("solicitante");
		log.info("Lista: " + lista.size());
	}
	
}
