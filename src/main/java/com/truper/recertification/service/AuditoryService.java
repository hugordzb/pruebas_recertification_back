package com.truper.recertification.service;

import java.util.Map;

import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.CountsEmployeeVO;

public interface AuditoryService {

	public Map<String, Object> findCuentas();
	
	public Map<String, Object> findCuentasSistema();
	
	public CountsBossVO findByBoss(String strIdBoss);
	
	public CountsEmployeeVO findEmployeeAcounts(String strIdEmployee);
	
}
