package com.truper.recertification.resources;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.conf.security.service.impl.AuthenticateServiceImpl;
import com.truper.recertification.vo.JwtAuthenticationResponse;
import com.truper.recertification.vo.JwtTokenUtil;
import com.truper.recertification.vo.JwtUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
@CrossOrigin(origins = "*")
@Api(value = "Authentication Api", description = "autentificación Single Sing-On")
public class AuthenticationResource {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    private AuthenticateServiceImpl authService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @PostMapping(path="/login")
    @ApiOperation(value = "Login para usuarios LDAP", 
    			  response = JwtAuthenticationResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
    						@ApiResponse(code = 401, message = "Unauthorized")})
    public JwtAuthenticationResponse createAuthenticationToken(
    		@ApiParam(value = "Usuario y password encriptado en base64 (user:password)", required = true)
    		@Valid @RequestHeader ("credential") String strCredential) throws Exception {
    	return this.authService.loginAndCreateToken(strCredential);
    }

    @GetMapping(path="/refresh")
    @ApiOperation(value = "Refresh Token", 
    			  response = JwtAuthenticationResponse.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
    public JwtAuthenticationResponse refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new JwtAuthenticationResponse(null, refreshedToken);
        } else {
            return new JwtAuthenticationResponse();
        }
    }
    
    /**
     * Intercepta todas las excepciones y los convierte en respuestas json
     * @param exception
     * @return
     */
   	@ExceptionHandler
   	@ResponseBody
   	public ResponseEntity<ExceptionRepresentation> handle(Exception exception) {
   		ExceptionRepresentation body = new ExceptionRepresentation(exception.getLocalizedMessage());
   		return new ResponseEntity<ExceptionRepresentation>(body, HttpStatus.UNAUTHORIZED);
   	}
    
    @AllArgsConstructor
    @Getter
    @Setter
    class ExceptionRepresentation {
    	private String message;
    }
}