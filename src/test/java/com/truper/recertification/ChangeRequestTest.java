package com.truper.recertification;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ChangeRequestTest {

	@Test
	public void pruebaJson() {
		String json = "{"
				+"'id':'46',"
				+ "'nombre':'Miguel',"
				+ "'empresa':'Autentia'}";
		
		Gson gson = new Gson();
	    Empleado empleado = gson.fromJson(json, Empleado.class);
	    
		 log.info("----"+ empleado.getId());
		 log.info("----"+ empleado.getNombre());
		 log.info("----"+ empleado.getEmpresa());

	}
}