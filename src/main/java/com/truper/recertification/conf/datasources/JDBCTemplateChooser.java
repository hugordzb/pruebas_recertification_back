package com.truper.recertification.conf.datasources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.truper.recertification.utils.constants.JNDIName;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCTemplateChooser {

	@Getter
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public JdbcTemplate getConecctionJdbcTemplate(String jndi) {
		
		try {
			switch(jndi) {
			case "Recert":
				jdbcTemplate = this.getJNDIJdbcTemplate(JNDIName.Recert);
				break;
			default:
				log.error("Error JDNI: " + jndi + " no encontrado");
				break;
			}
		}catch (Exception e) {
			log.error("Error JDNI: " + e.getMessage());
		}
		return jdbcTemplate;
	}
	
	private JdbcTemplate getJNDIJdbcTemplate(JNDIName jndiName) {
		try {
			log.info("El jndi se llama: " + "jdbc" + jndiName.toString());
			return applicationContext.getBean("jdbc" + jndiName.toString(), JdbcTemplate.class);
		}catch(Exception e) {
			log.error("Error al escoger jndi");
			return null;
		}
	}
}