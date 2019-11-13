package com.truper.recertification.dao;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.model.ReTokenEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReToken {
	
	@Autowired
	private ReTokenDAO tokenDAO;
	
	@Test
	public void findByUltimaSesion() {
		ReTokenEntity lista = tokenDAO.findByUltimaSesion(new Timestamp(2019, 11, 11, 11, 11, 11, 11));
		log.info("Lista: " + lista);
	}
}
