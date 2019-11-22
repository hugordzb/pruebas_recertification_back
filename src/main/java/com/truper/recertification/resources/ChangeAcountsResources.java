package com.truper.recertification.resources;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*")
@RestController
public class ChangeAcountsResources {

	@Autowired
	private ChangeAcountsService changeService;
	
	@PutMapping(path="/requestChange")
    public Integer requestChange(@RequestBody String json) {
		return changeService.requestChange(json);
    }
	
	@PutMapping(path="/processChange")
    public void processChange(@RequestBody String json) {
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
