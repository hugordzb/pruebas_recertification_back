package com.truper.recertification.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.InfoService;
import com.truper.recertification.vo.answer.ProfileSystemListVO;
import com.truper.recertification.vo.answer.SystemsListVO;

@CrossOrigin(origins = "*")
@RestController
public class InfoResources {

	@Autowired
	private InfoService infoService;
	
	@GetMapping(path = "/auditableSystems")
    public SystemsListVO findAuditableSystems() {
            return infoService.getSystems();
    }
	
	@GetMapping(path = "/profileSystems")
	public ProfileSystemListVO findProfileSystems() {
		return infoService.getProfileSytem();
	}
	
}
