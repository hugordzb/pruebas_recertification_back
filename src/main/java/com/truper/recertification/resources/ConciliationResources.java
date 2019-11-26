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
import com.truper.recertification.excel.service.LoadLayoutCiatService;
import com.truper.recertification.excel.service.LoadLayoutRecertService;
import com.truper.recertification.excel.utils.constants.ExcelCiatSheet;
import com.truper.recertification.excel.utils.constants.ExcelRecertificationSheet;
import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.excel.vo.CiatLineaExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.exception.ProfilesException;
import com.truper.recertification.exception.RecertificationException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/conciliacion")
public class ConciliationResources {
	
	@Autowired
	private ExcelRowToVOService<RecertificationExcelVO> readRecert;
	
	@Autowired
	private ExcelRowToVOService<CiatExcelVO> readCiat;
	
	@Autowired
	private LoadLayoutRecertService loadLayoutRecertService;
	
	@Autowired
	private LoadLayoutCiatService loadlayoutCiatService;
	
	@PostMapping("/recertificacion")
	public List<RecertificationExcelVO> processRecertification(@RequestParam(name="file", required = false) MultipartFile file) 
			throws IOException, RecertificationException{
	
		if(file == null)
			throw new RecertificationException("Debes enviar un archivo");
		
		List<RecertificationExcelVO> listData = null;
		try {
			listData = this.readRecert.readExcelRercertificationToVo(file.getInputStream(), ExcelRecertificationSheet.ACTIVOS);
			
			if(listData  != null) {
				this.loadLayoutRecertService.insertUsersData(listData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listData;
    }
	
	@PostMapping("/ciatProfiles")
	public List<CiatExcelVO> processCiatProfiles(@RequestParam(name="file", required = false) MultipartFile file) 
			throws IOException, ProfilesException{
	
		if(file == null)
			throw new ProfilesException("Debes enviar un archivo");
		
		List<CiatExcelVO> listData = null;
		List<CiatLineaExcelVO> listLineData = null;
		
		try {
			listData = this.readCiat.readExcelCiatToVo(file.getInputStream(), ExcelCiatSheet.CIAT);
			
			if(listData  != null) {
				this.loadlayoutCiatService.insertUsersData(listData);
			}
			
//			listLineData = this.readCiat.readExcelCiatToVo(file.getInputStream(), ExcelCiatSheet.CIATENLINEA);
//			
//			if(listLineData  != null) {
//				this.loadlayoutCiatService.insertUsersData(listData);
//			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listData;
    }
}
