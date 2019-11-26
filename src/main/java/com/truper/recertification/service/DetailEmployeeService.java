package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AccountDataVO;

public interface DetailEmployeeService {

	/**
	 * This is an auxiliary method to find employ detail
	 * @param strIdUsuario
	 * @return counts by Employ
	 */
	public DetailCountsEmployeeVO findEmployDetail(ReUsuarioEntity userEntity);
	
	/**
	 * This is an auxiliary method to separate acounts systems
	 * @param cuentasUsuario
	 * @param lstTel
	 * @param lstSap
	 * @param lstCiat
	 */
	public void findAcounts(ReCuentasUsuarioEntity cuentasUsuario, List<AccountDataVO> lstTel);
}
