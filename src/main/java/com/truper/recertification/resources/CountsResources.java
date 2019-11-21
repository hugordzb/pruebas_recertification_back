package com.truper.recertification.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.AuditoryService;
import com.truper.recertification.vo.answer.CountsBossVO;
import com.truper.recertification.vo.answer.CountsEmployeeVO;

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
		
}
