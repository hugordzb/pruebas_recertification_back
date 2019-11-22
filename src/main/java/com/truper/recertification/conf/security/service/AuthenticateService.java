package com.truper.recertification.conf.security.service;

import com.truper.recertification.vo.JwtAuthenticationResponse;

public interface AuthenticateService {

	/**
	 * Login method review on AD, and create Token
	 * @param strCredencial
	 * @return JwtAuthenticationResponse
	 */
	public JwtAuthenticationResponse loginAndCreateToken(String strCredencial);

}
