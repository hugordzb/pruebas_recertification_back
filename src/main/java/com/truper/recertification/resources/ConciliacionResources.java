package com.truper.recertification.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.truper.recertification.excel.service.impl.RecertificationDocsServiceImpl;
import com.truper.recertification.excel.vo.FileVO;
import com.truper.recertification.exception.RecertificationException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/conciliacion")
public class ConciliacionResources {

	@Autowired
	private RecertificationDocsServiceImpl recertDocs;
	
	@PostMapping("/recertificacion")
	public FileVO processRecertification(@RequestParam(name="file", required = false)MultipartFile file) 
			throws IOException, RecertificationException{
		this.recertDocs.loadRecert(file.getOriginalFilename(), file.getInputStream());
		return null;
    }
}
