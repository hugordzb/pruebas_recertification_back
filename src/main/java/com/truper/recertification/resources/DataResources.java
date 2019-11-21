package com.truper.recertification.resources;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.DataService;
import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.SystemsListVO;

@CrossOrigin(origins = "*")
@RestController
public class DataResources {

	@Autowired
	private DataService infoService;
	
	@GetMapping(path = "/auditableSystems")
    public SystemsListVO findAuditableSystems() {
            return infoService.getSystems();
    }
	
	@GetMapping(path = "/profileSystems")
	public ProfileSystemListVO findProfileSystems() {
		return infoService.getProfileSytem();
	}
	
	@GetMapping(path = "/binnacle")
	public Map<String, Object> findChangeControl(){
		return infoService.getChangeControl();
	}
	
}
