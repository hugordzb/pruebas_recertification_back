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
    public Integer requestChange(@RequestBody String json) {
		return changeService.requestChange(json);
    }
	
	@PutMapping(path="/processChange")
    public void processChange(@RequestBody String json) {
		changeService.processChange(json);
    }
}
