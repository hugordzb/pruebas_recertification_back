package com.truper.recertification.resources;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*")
@RestController
public class RecertificationResource {

	@Autowired
	private RecertificationService recertification;
	
	@GetMapping(path = "/sendRecertification/{idJefe}")
    public Boolean sendMail(@PathVariable ("idJefe") String strIdJefe) {
            return recertification.sendMail(strIdJefe);
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
