package com.truper.recertification.service;

import java.util.List;
import java.util.Map;

import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.CountsEmployeeVO;

public interface AuditoryService {

	/**
	 * This method return all recertification information order by boss and systems
	 * @return map
	 */
	public Map<String, Object> findCuentasSistema();
	
	/**
	 * This method find by boss
	 * @param strIdBoss
	 * @return acounts data by systems and employee
	 */
	public CountsBossVO findByBoss(String strIdBoss);
	
	public List<CountsEmployeeVO> generateLetterByBoss(String strIdBoss);
	
	/**
	 * This methos find by employee
	 * @param strIdEmployee
	 * @return acounts data by systems
	 */
	public CountsEmployeeVO findEmployeeAcounts(String strIdEmployee);
	
}
