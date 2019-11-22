package com.truper.recertification.resources;

import java.util.Map;

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
import com.truper.recertification.vo.answer.CountsEmployeeVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@CrossOrigin(origins = "*")
@RestController
public class CountsResources {

	@Autowired
	private AuditoryService auditoryService;
			
	@GetMapping(path = "/auditableAcounts")
    public Map<String, Object> findAuditableAcounts() {
            return auditoryService.findCuentasSistema();
    }
	
	@GetMapping(path = "/bossDetail/{idJefe}")
	public CountsBossVO findByBoss(@PathVariable ("idJefe") String strIdJefe) {
		return auditoryService.findByBoss(strIdJefe);
	}
	
	@GetMapping	(path = "/employeeDetail/{strIdEmployee}")
	public CountsEmployeeVO findByEmployee (@PathVariable("strIdEmployee") String strIdEmployee) {
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
