package com.truper.recertification.service;

import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;

public interface DetailLetterService {


	/**
	 * This is an auxiliary method to find employ detail
	 * @param strIdUsuario
	 * @return counts by Employ
	 */
	public DetailCountsEmployeeVO findEmployDetail(String strIdUsuario);
}
