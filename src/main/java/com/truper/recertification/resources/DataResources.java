package com.truper.recertification.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.DataService;
import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.SystemsListVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	  			  response = SystemsListVO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
    public SystemsListVO findAuditableSystems()  throws Exception{
            return infoService.getSystems();
    }
	
	@GetMapping(path = "/profileSystems")
	@ApiOperation(value = "Recupera los perfiles auditables", 
				  response = ProfileSystemListVO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public ProfileSystemListVO findProfileSystems()  throws Exception{
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
