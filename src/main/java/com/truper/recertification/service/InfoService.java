package com.truper.recertification.service;

import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.SystemsListVO;

public interface InfoService {

	public SystemsListVO getSystems();

	public ProfileSystemListVO getProfileSytem();
	
}
