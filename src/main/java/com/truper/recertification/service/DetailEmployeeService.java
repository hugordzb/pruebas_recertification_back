package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AcountsVO;
import com.truper.recertification.vo.answer.systems.CiatDataVO;
import com.truper.recertification.vo.answer.systems.ListAcountsVO;
import com.truper.recertification.vo.answer.systems.SapDataVO;
import com.truper.recertification.vo.answer.systems.TelDataVO;

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
	public AcountsVO orderCounts(ListAcountsVO lstAcountsVO);
}
