package com.truper.recertification.conf.security.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.truper.recertification.utils.constants.AuthorityName;
import com.truper.recertification.vo.Authority;
import com.truper.recertification.vo.JwtUser;
import com.truper.recertification.vo.UserVO;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Value("${app.security.user}")
	private String strUsername;
	
	@Value("${app.security.password}")
	private String strPassword;
	
    @Override
    public JwtUser loadUserByUsername(String strUsername) throws UsernameNotFoundException {
    	
    	UserVO user = new UserVO();
    	user.setUsername(this.strUsername);
    	user.setPassword(this.strPassword);
    	
    	Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
    	
        Authority rol = new Authority();
        rol.setId(new Long(1));
        rol.setName(AuthorityName.ROLE_ADMIN);
        
        Authority rol2 = new Authority();
        rol2.setId(new Long(2));
        rol2.setName(AuthorityName.ROLE_USER);
        
        List<Authority> roles = new ArrayList<Authority>();
        roles.add(rol);
        roles.add(rol2);
        
    	return new JwtUser(
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(roles),
                true,
                calendar.getTime(), user);
    }
    
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }
}