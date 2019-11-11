package com.truper.recertification.vo;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 12423545342L;
	
    private final String username;
	
	@JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
	
	@JsonIgnore
    private final Date lastPasswordResetDate;
	
	@JsonIgnore
	private UserVO user;
	
    public JwtUser(
    		String username,
    		String password, Collection<? extends GrantedAuthority> authorities,
    		boolean enabled,
    		Date lastPasswordResetDate, 
    		UserVO user) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.user = user;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
