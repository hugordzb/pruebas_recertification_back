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

import com.truper.recertification.service.DataService;
import com.truper.recertification.vo.answer.BossDetailVO;
import com.truper.recertification.vo.answer.ProfileSystemVO;
import com.truper.recertification.vo.answer.SystemsVO;

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
@Api(value = "Información Auxiliar", 
	 description = "Información que se muestra para brindarle "
	 		+ "al usuario opciones a seleccionar, que ayudarán a "
	 		+ "evitar peticiones innecesarias")
public class DataResources {

	@Autowired
	private DataService infoService;
	
	@GetMapping(path = "/auditableSystems")
	@ApiOperation(value = "Recupera los Sistemas Auditables", 
	  			  response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
    public List<SystemsVO> findAuditableSystems()  throws Exception{
            return infoService.getSystems();
    }
	
	@GetMapping(path = "/profileSystems")
	@ApiOperation(value = "Recupera los perfiles auditables", 
				  response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public List<ProfileSystemVO> findProfileSystems()  throws Exception{
		return infoService.getProfileSytem();
	}
	
	@GetMapping(path = "/binnacle")
	@ApiOperation(value = "Recupera los tickets de Control de Cambios", 
	  			  response = Map.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public Map<String, Object> findChangeControl()  throws Exception{
		return infoService.getChangeControl();
	}
	
	@GetMapping(path = "/bossData/{idJefe}")
	@ApiOperation(value = "Recupera la información del Jefe",
				  response = BossDetailVO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public BossDetailVO findBossData(
			@ApiParam(value = "Cuenta AD (LDAP) del jefe)", required = true)
    		@Valid @PathVariable ("idJefe") String idJefe) {
		return infoService.findByBoss(idJefe);
	}
	
	@GetMapping(path = "/bossesData")
	@ApiOperation(value = "Recupera la información de todos los Jefes",
				  response = Map.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public Map<String, Object> findBossesData() {
		return infoService.findByAllBoss();
	}
	
	 /**
     * Intercepta todas las excepciones y los convierte en respuestas json
     * @param exception
     * @return
     */
   	@ExceptionHandler
   	@ResponseBody
   	public ResponseEntity<ExceptionRepresentation> handle(Exception exception) {
   		ExceptionRepresentation body = new ExceptionRepresentation(
   				exception.getLocalizedMessage());
   		return new ResponseEntity<ExceptionRepresentation>(body, 
   				HttpStatus.UNAUTHORIZED);
   	}
    
    @AllArgsConstructor
    @Getter
    @Setter
    class ExceptionRepresentation {
    	private String message;
    }
}
