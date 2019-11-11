package com.truper.recertification.conf.security.service.impl;

import java.util.Base64;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.truper.recertification.conf.security.AuthenticationException;
import com.truper.recertification.conf.security.CustomAuthenticationManager;
import com.truper.recertification.conf.security.service.AuthenticateService;
import com.truper.recertification.service.SystemsServices;
import com.truper.recertification.vo.JwtAuthenticationResponse;
import com.truper.recertification.vo.JwtTokenUtil;
import com.truper.recertification.vo.JwtUser;
import com.truper.recertification.vo.UserVO;

@Service
public class AuthenticateServiceImpl implements AuthenticateService{
	
	@Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private CustomAuthenticationManager authenticationManager;
	
	SystemsServices sistema;
	
	public JwtAuthenticationResponse loginAndCreateToken(String strCredencial) {
		UserVO user = this.decoder(strCredencial);
    	authenticate(user);
        
        JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(user.getUsername());
        String strToken = jwtTokenUtil.generateToken(userDetails);
        return new JwtAuthenticationResponse(user.getUsername(), strToken);
	}
	
	/* Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown 
	 * */
	private void authenticate(UserVO user) {
		Objects.requireNonNull(user.getUsername());
		Objects.requireNonNull(user.getPassword());
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		} catch (DisabledException e) {
			throw new AuthenticationException("Usuario deshabilitado", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Usuario y/o constraseña incorrectos", e);
		}
	}
	
	private UserVO decoder(String strEncoded) {
		Base64.Decoder decoder = Base64.getDecoder();

		String strCode = new String(decoder.decode(strEncoded));
				
		int intStart = strCode.indexOf(":");
		int intEnd = strCode.indexOf(":", intStart - intStart);
		
		return UserVO.builder()
			.username(strCode.substring(intStart-intStart, intEnd))
			.password(strCode.substring(intStart + 1))
			.build();
	}
	
}