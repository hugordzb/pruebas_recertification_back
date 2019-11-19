package com.truper.recertification;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.service.AuditoryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ServicesRecertificationTest {

	@Autowired
	private AuditoryService auditoryService;
	
	@Test
	public void findCuentas() {
		log.info("Map: " + auditoryService.findCuentas());
	}
	
	@Test
	public void findCuentasSistema() {
		Map<String, Object>  sysMap = auditoryService.findCuentasSistema();
		log.info("MAp: " + sysMap);
	}
}
