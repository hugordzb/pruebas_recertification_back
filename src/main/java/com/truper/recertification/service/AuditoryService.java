package com.truper.recertification.service;

import java.util.List;
import java.util.Map;

import com.truper.recertification.vo.answer.AcountsBossVO;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;

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
	public AcountsBossVO findByBoss(String strIdBoss);
	
	/**
	 * This method generate a letter by boss
	 * @param strIdBoss
	 * @return List<DetailCountsEmployeeVO>
	 */
	public List<DetailCountsEmployeeVO> generateLetterByBoss(String strIdBoss);
	
}
