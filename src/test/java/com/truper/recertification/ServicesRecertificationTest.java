package com.truper.recertification;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.service.ChangeAcountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ServicesRecertificationTest {

	@Autowired
	private AuditoryService auditoryService;
	
	@Autowired
	private ChangeAcountsService changeService;
	
	@Test
	public void findCuentasSistema() {
		Map<String, Object>  sysMap = auditoryService.findCuentasSistema();
		log.info("MAp: " + sysMap);
	}
	
	@Test
	public void fecha() {
		
	    Date myDate = new Date();
	    log.info("Date: " + myDate);
	    log.info("New: " + new SimpleDateFormat("MMyy").format(myDate));
	    int intDate = Integer.parseInt(new SimpleDateFormat("MM").format(myDate));
	    String da;
	    if(intDate <= 06) {
	    	log.info("mes: " + intDate);
	    	da = "01" + new SimpleDateFormat("yy").format(myDate);
	    	log.info("mesNuevo: " + da);
	    }else {
	    	log.info("mes: " + intDate);
	    	da = "02" + new SimpleDateFormat("yy").format(myDate);
	    	log.info("mesNuevo: " + da);
	    }
	}
	
	@Test
	public void requestChangeAlta() {
		String json ="{"  
				+ "'tipoMov':'A',"
				+ "'idUsuario':'cgarcias',"
				+ "'nIdUsuario':'cgarcias',"
				+ "'perfil':'admin',"
				+ "'sistema':'TEL'," 
				+ "'nCuentaSistema':'cuentanueva',"
				+ "'idJefe':'jefe',"
				+ "'solicitante':'bcavazos'" 
				+ "}";
		
		log.info("Ticket: " +changeService.requestChange(json));
	}
	
	@Test
	public void requestChangeBajaBoss() {
		String json ="{"  
				+ "'tipoMov':'B',"
				+ "'idUsuario':'cgarcias',"
				+ "'idJefe':'jefecito',"
				+ "'solicitante':'bcavazos'" 
				+ "}";
		
		log.info("Ticket: " +changeService.requestChange(json));
	}
	
	@Test
	public void requestChangeBaja() {
		String json ="{"  
				+ "'tipoMov':'B',"
				+ "'idUsuario':'cgarcias',"
				+ "'nIdUsuario':'cgarcias',"
				+ "'perfil':'admin',"
				+ "'sistema':'TEL'," 
//				+ "'nPerfil':'1',"
//				+ "'nSistema':'CIAT',"
//				+ "'cuentaSistema':'cuenta',"
				+ "'nCuentaSistema':'cuentanueva',"
				+ "'idJefe':'jefe',"
//				+ "'nIdJefe':'jefecito',"
				+ "'solicitante':'bcavazos'" 
				+ "}";
		
		log.info("Ticket: " +changeService.requestChange(json));
	}
}
