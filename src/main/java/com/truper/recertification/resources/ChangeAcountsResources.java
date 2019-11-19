package com.truper.recertification.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.ChangeAcountsService;

@CrossOrigin(origins = "*")
@RestController
public class ChangeAcountsResources {

	@Autowired
	private ChangeAcountsService changeService;
	
	@PutMapping(path="/requestChange")
    public String requestChange(@RequestBody String json) {
		return changeService.requestAcount(json);
    }
}
