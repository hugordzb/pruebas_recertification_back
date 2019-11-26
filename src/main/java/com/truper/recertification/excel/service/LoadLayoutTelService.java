package com.truper.recertification.excel.service;

import java.util.List;

import com.truper.recertification.excel.vo.TelExcelVO;
import com.truper.recertification.exception.ProfilesException;
import com.truper.recertification.model.RePerfilSistemaEntity;

public interface LoadLayoutTelService {

	public List<RePerfilSistemaEntity> insertUsersData(List<TelExcelVO> listData) 
			throws ProfilesException;
}
