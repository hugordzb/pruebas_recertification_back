package com.truper.recertification;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.service.AuditoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TestServices {

	@Autowired
	private AuditoryService auditoryService;
	
	@Test
	public void auditableSystems() {
		log.info("Lista: " + auditoryService.getSystems());
	}
	
	@Test
	public void auditableAcounts() {
		log.info("Map: " + auditoryService.findCuentasSistema());
	}
	
	@Test
	public void auditableEmploy() {
		
	}
}
