package com.truper.recertification.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.ChangeAcountsService;

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
@Api(value = "Solicitud para Mesa de Servicio", description = "Solicitar, revisar y validar los cambios asociados a la recertificacion")
public class ChangeAcountsResources {

	@Autowired
	private ChangeAcountsService changeService;
	
	@PutMapping(path="/requestChange")
    @ApiOperation(value = "Solicitar un Cambio con perfil Jefe",
    			  response = Integer.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public Integer requestChange(
			@ApiParam(value = "Json con el detalle del cambio solicitado (Alta, Baja o Modificación)", required = true)
    		@Valid	@RequestBody String json) {
		return changeService.requestChange(json);
    }
	
	@PutMapping(path="/processChange")
	@ApiOperation(value = "Revisar y autorizar, el cambio solicitado con perfil Mesa de Servicio", 
				  response = void.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 401, message = "Unauthorized")})
	public void processChange(
			@ApiParam(value = "Json con el detalle del cambio revisado por Mesa de Servicio (Aprobado/No aprobado)", required = true)
			@Valid	@RequestBody String json) {
		changeService.processChange(json);
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
