package com.truper.recertification.resources;

import java.util.List;
import java.util.Map;

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

import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;

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
@Api(value = "Detalle de Cuentas", description = "Detalle de cuentas para la recertificación")
public class CountsResources {

	@Autowired
	private AuditoryService auditoryService;
			
	@GetMapping(path = "/auditableAcounts")
	@ApiOperation(value = "Detalle de todas las Cuentas relacionadas a la recertificación", 
	  			  response = Map.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
    public Map<String, Object> findAuditableAcounts() {
            return auditoryService.findCuentasSistema();
    }
	
	@GetMapping(path = "/bossDetail/{idJefe}")
	@ApiOperation(value = "Detalle de las Cuentas relacionadas a la recertificación, por jefe", 
	  			  response = Map.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public CountsBossVO findByBoss(
			@ApiParam(value = "Cuenta AD (LDAP) del jefe", required = true)
    		@Valid	@PathVariable ("idJefe") String strIdJefe) {
		return auditoryService.findByBoss(strIdJefe);
	}
	
	@GetMapping(path = "/letterService/{idJefe}")
	@ApiOperation(value = "Detalle de las Cuentas relacionadas a la recertificación, por jefe", 
	  			  response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public List<DetailCountsEmployeeVO> letterService(
			@ApiParam(value = "Cuenta AD (LDAP) del jefe", required = true)
    		@Valid	@PathVariable ("idJefe") String strIdJefe) {
		return auditoryService.generateLetterByBoss(strIdJefe);
	}
	
	@GetMapping	(path = "/employeeDetail/{strIdEmployee}")
	@ApiOperation(value = "Detalle de las Cuentas relacionadas a la recertificación por empleado", 
				  response = DetailCountsEmployeeVO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public DetailCountsEmployeeVO findByEmployee (
			@ApiParam(value = "Cuenta AD (LDAP) del empleado)", required = true)
    		@Valid	@PathVariable("strIdEmployee") String strIdEmployee) {
		return auditoryService.findEmployeeAcounts(strIdEmployee);
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
