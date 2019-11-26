package com.truper.recertification.service;

import java.util.List;

import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.exception.RecertificationException;
import com.truper.recertification.model.ReUsuarioEntity;

public interface LoadLayoutRecertService {
	
	public List<ReUsuarioEntity> insertUsersData(List<RecertificationExcelVO> listData) 
			throws RecertificationException;
	
}
