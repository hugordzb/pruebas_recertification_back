package com.truper.recertification.excel.service;

import java.util.List;

import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.exception.ProfilesException;
import com.truper.recertification.model.RePerfilSistemaEntity;

public interface LoadLayoutCiatService {

	public List<RePerfilSistemaEntity> insertUsersData(List<CiatExcelVO> listData) throws ProfilesException;
}
