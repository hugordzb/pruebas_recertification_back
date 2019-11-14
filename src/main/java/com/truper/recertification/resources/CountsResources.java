package com.truper.recertification.resources;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.AuditoryService;

@CrossOrigin(origins = "*")
@RestController
public class CountsResources {

	@Autowired
	private AuditoryService auditoryService;
	
	@GetMapping(path="/auditableSystems")
    public List<String> findAuditableSystems() {
            return auditoryService.getSystems();
    }
	
	@GetMapping(path="/auditableAcounts")
    public Map<String, Object> findAuditableAcounts() {
            return auditoryService.findCuentasSistema();
    }
	
	@GetMapping(path="/auditable")
    public Map<String, Object> findAuditable() {
            return auditoryService.findCuentas();
    }
}
