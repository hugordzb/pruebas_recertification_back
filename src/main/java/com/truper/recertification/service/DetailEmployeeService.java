package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.vo.answer.CountsEmployeeVO;
import com.truper.recertification.vo.answer.sistemas.CiatDataVO;
import com.truper.recertification.vo.answer.sistemas.CountsVO;
import com.truper.recertification.vo.answer.sistemas.ListAcountsVO;
import com.truper.recertification.vo.answer.sistemas.SapDataVO;
import com.truper.recertification.vo.answer.sistemas.TelDataVO;

public interface DetailEmployeeService {

	public CountsEmployeeVO findEmployDetail(String strIdUsuario);
	public void findAcounts(ReCuentasUsuarioEntity cuentasUsuario, List<TelDataVO> lstTel,
			List<SapDataVO> lstSap, List<CiatDataVO> lstCiat);
	public List<CountsVO> orderCounts(ListAcountsVO lstAcountsVO);
}
