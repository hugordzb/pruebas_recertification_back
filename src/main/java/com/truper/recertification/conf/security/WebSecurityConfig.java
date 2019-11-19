package com.truper.recertification.conf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.truper.recertification.conf.security.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	public JwtAuthorizationTokenFilter authenticationTokenFilter;
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() //Don´t create session because is rest services
            .authorizeRequests()
            .antMatchers("/api/v1/login**").permitAll()
            .antMatchers("/api/v1/refresh**").permitAll()
            .antMatchers("/api/v1/swagger-resources/**").permitAll()
            .antMatchers("/api/v1/swagger-ui.html").permitAll()
            .antMatchers("/api/v1/webjars/**").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .anyRequest().authenticated();
        
       httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity
            .headers()
            .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
            .cacheControl();
    }

    @Override
    public void configure(WebSecurity web) {
        // AuthenticationTokenFilter will ignore the below paths
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login")
        	.antMatchers("/api/v1/v2/api-docs/**")
        	.antMatchers("/api/v1/swagger.json")
        	.antMatchers("/api/v1/swagger-ui.html")
        	.antMatchers("/api/v1/swagger-resources/**")
        	.antMatchers("/api/v1/webjars/**");
    }	
    
}