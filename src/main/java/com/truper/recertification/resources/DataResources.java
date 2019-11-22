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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*")
@RestController
public class DataResources {

	@Autowired
	private DataService infoService;
	
	@GetMapping(path = "/auditableSystems")
    public SystemsListVO findAuditableSystems()  throws Exception{
            return infoService.getSystems();
    }
	
	@GetMapping(path = "/profileSystems")
	public ProfileSystemListVO findProfileSystems()  throws Exception{
		return infoService.getProfileSytem();
	}
	
	@GetMapping(path = "/binnacle")
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
