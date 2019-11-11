package com.truper.recertification.conf.security.service;

import com.truper.recertification.vo.JwtAuthenticationResponse;

public interface AuthenticateService {

	public JwtAuthenticationResponse loginAndCreateToken(String strCredencial);

}
