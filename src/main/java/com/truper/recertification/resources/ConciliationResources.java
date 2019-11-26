package com.truper.recertification.resources;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.truper.recertification.excel.mapper.ExcelRowToVOService;
import com.truper.recertification.excel.service.LoadLayoutRecertService;
import com.truper.recertification.excel.utils.constants.ExcelRecertificationSheet;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.exception.RecertificationException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/conciliacion")
public class ConciliationResources {
	
	@Autowired
	private ExcelRowToVOService<RecertificationExcelVO> readRecert;
	
	@Autowired
	private LoadLayoutRecertService loadLayoutService;
	
	@PostMapping("/recertificacion")
	public List<RecertificationExcelVO> processRecertification(@RequestParam(name="file", required = false) MultipartFile file) 
			throws IOException, RecertificationException{
	
		if(file == null)
			throw new RecertificationException("Debes enviar un archivo");
		
		List<RecertificationExcelVO> listData = null;
		try {
			listData = this.readRecert.readExcelRercertificationToVo(file.getInputStream(), ExcelRecertificationSheet.ACTIVOS);
			
			if(listData  != null) {
				this.loadLayoutService.insertUsersData(listData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listData;
    }
}
