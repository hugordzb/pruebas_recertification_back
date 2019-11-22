package com.truper.recertification.conf.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.truper.recertification.vo.JwtTokenUtil;

@Slf4j
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final String strtokenHeader;

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, 
    		JwtTokenUtil jwtTokenUtil, @Value("${jwt.header}") String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.strtokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    		FilterChain chain) throws ServletException, IOException {
        
    	log.debug("processing authentication for '{}'", request.getRequestURL());
        final String strRequestHeader = request.getHeader(this.strtokenHeader);

        String strUsername = null;
        String strAuthToken = null;
        if (strRequestHeader != null && strRequestHeader.startsWith("Bearer ")) {
        	strAuthToken = strRequestHeader.substring(7);
            try {
            	strUsername = jwtTokenUtil.getUsernameFromToken(strAuthToken);
            } catch (IllegalArgumentException e) {
                log.info("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                log.info("the token is expired and not valid anymore", e);
            } catch (Exception exG){
            	exG.printStackTrace();
            	log.info("Error  en authenticación");
            }
        }

        log.debug("checking authentication for user '{}'", strUsername);
        if (strUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            log.info("security context was null, so authorizating user");

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it. It's up to you ;)
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(strUsername);

            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly. Again it's up to you ;)
            if (jwtTokenUtil.validateToken(strAuthToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                		new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authorizated user '{}', setting security context", strUsername);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}