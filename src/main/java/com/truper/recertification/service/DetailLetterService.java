package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AccountDataVO;
import com.truper.recertification.vo.answer.systems.CiatDataVO;
import com.truper.recertification.vo.answer.systems.SapDataVO;

public interface DetailLetterService {


	/**
	 * This is an auxiliary method to find employ detail
	 * @param strIdUsuario
	 * @return counts by Employ
	 */
	public DetailCountsEmployeeVO findEmployDetail(String strIdUsuario);
	
	/**
	 * This is an auxiliary method to separate acounts systems
	 * @param cuentasUsuario
	 * @param lstTel
	 * @param lstSap
	 * @param lstCiat
	 */
	public void findAcounts(ReCuentasUsuarioEntity cuentasUsuario, List<AccountDataVO> lstTel,
							List<SapDataVO> lstSap, List<CiatDataVO> lstCiat);
}
