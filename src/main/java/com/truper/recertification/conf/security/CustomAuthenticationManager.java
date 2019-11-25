package com.truper.recertification.conf.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
	
	@Value("${app.sso.url.check}")
	private String urlSSOCheck;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String strUsuario = authentication.getName();
		String strPassword = authentication.getCredentials().toString();
		
		this.checkSSO(strPassword);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(strUsuario, strPassword, authorities);
	}
	
	private void checkSSO(String token) throws BadCredentialsException {
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Bearer " + token);
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		
		ResponseEntity<String> result = restTemplate.exchange(this.urlSSOCheck, HttpMethod.GET, entity, String.class);
		if(result.getStatusCode().value() != 200) {
			throw new BadCredentialsException("Credenciales de SSO incorrectas");
		}
	}
	
}