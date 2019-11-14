package com.truper.recertification.service;

import java.util.List;
import java.util.Map;

public interface AuditoryService {

	public List<String> getSystems();

	public Map<String, Object> findCuentas();
	
	public Map<String, Object> findCuentasSistema();
	
}
