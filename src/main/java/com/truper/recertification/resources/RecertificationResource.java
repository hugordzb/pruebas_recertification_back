package com.truper.recertification.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.truper.recertification.service.RecertificationService;

@CrossOrigin(origins = "*")
@RestController
public class RecertificationResource {

	@Autowired
	private RecertificationService recertification;
	
	@GetMapping(path = "/sendRecertification/{idJefe}")
    public Boolean sendMail(@PathVariable ("idJefe") String strIdJefe){
            return recertification.sendMail(strIdJefe);
    }
}
