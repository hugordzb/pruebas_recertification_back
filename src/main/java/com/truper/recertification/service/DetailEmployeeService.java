package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.sistemas.CiatDataVO;
import com.truper.recertification.vo.answer.sistemas.AcountsVO;
import com.truper.recertification.vo.answer.sistemas.ListAcountsVO;
import com.truper.recertification.vo.answer.sistemas.SapDataVO;
import com.truper.recertification.vo.answer.sistemas.TelDataVO;

public interface DetailEmployeeService {

	/**
	 * This is an auxiliary method to find employ detail
	 * @param strIdUsuario
	 * @return counts by Employ
	 */
	public CountsEmployeeVO findEmployDetail(String strIdUsuario);
	
	/**
	 * This is an auxiliary method to separate acounts systems
	 * @param cuentasUsuario
	 * @param lstTel
	 * @param lstSap
	 * @param lstCiat
	 */
	public void findAcounts(ReCuentasUsuarioEntity cuentasUsuario, List<TelDataVO> lstTel,
							List<SapDataVO> lstSap, List<CiatDataVO> lstCiat);
	/**
	 * This is an auxiliary method to order data on a list
	 * the principal function is remove logic from front 
	 * @param lstAcountsVO
	 * @return
	 */
	public List<AcountsVO> orderCounts(ListAcountsVO lstAcountsVO);
}
