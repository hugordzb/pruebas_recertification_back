package com.truper.recertification.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.RecertificationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Solicitud para recertificar", 
	 description = "Se genera el envio de correos para solicitar "
	 		+ "la validación de las cuentas de usuarios; "
	 		+ "y realizar la recertificación")
public class RecertificationResource {

	@Autowired
	private RecertificationService recertification;
	
	@GetMapping(path = "/sendRecertification/{idJefe}")
	@ApiOperation(value = "Envio de la Carta para la Recertificación", 
				  response = Boolean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
    public Boolean sendMail(
    		@ApiParam(value = "Cuenta AD (LDAP) del jefe", required = true)
    		@Valid	@PathVariable ("idJefe") String strIdJefe) {
            return recertification.sendMail(strIdJefe);
    }
	
	
	@GetMapping(path = "/recertifyBoss/{periodo}/{idJefe}")
	@ApiOperation(value = "Se recertifica el jefe", 
				  response = Boolean.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
    public Boolean recertifyBoss(
    		@ApiParam(value = "Cuenta AD (LDAP) del jefe", required = true)
    		@PathVariable ("idJefe") String strIdJefe, 
    		@PathVariable ("periodo") String strPeriodo) {
            return recertification.recertifyBoss(strIdJefe, strPeriodo);
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
