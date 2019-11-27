package com.truper.recertification.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReRecertificacionDAO;
import com.truper.recertification.excel.mapper.ExcelRowToVOService;
import com.truper.recertification.excel.service.LoadLayoutCiatService;
import com.truper.recertification.excel.service.LoadLayoutRecertService;
import com.truper.recertification.excel.service.LoadLayoutTelService;
import com.truper.recertification.excel.utils.constants.ExcelCiatSheet;
import com.truper.recertification.excel.utils.constants.ExcelRecertificationSheet;
import com.truper.recertification.excel.utils.constants.ExcelTelSheet;
import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.excel.vo.TelExcelVO;
import com.truper.recertification.exception.ProfilesException;
import com.truper.recertification.exception.RecertificationException;
import com.truper.recertification.model.PKRecertificacion;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReRecertificacionEntity;
import com.truper.recertification.vo.answer.BossDetailVO;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/conciliacion")
public class ConciliationResources {
	
	@Autowired
	private ExcelRowToVOService<RecertificationExcelVO> readRecert;
	
	@Autowired
	private ExcelRowToVOService<CiatExcelVO> readCiat;
	
	@Autowired
	private ExcelRowToVOService<TelExcelVO> readTel;
	
	@Autowired
	private LoadLayoutRecertService loadLayoutRecertService;
	
	@Autowired
	private LoadLayoutCiatService loadlayoutCiatService;
	
	@Autowired
	private LoadLayoutTelService loadlayoutTelService;
	
	@Autowired
	private ReDetalleJefeDAO daoDetalleJefe;
	
	@Autowired
	private ReDepartamentoDAO daoDepartamento;
	
	@Autowired 
	private ReRecertificacionDAO daoRecertificacion;
	
	@PostMapping("/recertificacion")
	public List<BossDetailVO> processRecertification(@RequestParam(name="file", required = false) MultipartFile file) 
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
		
		List<String> lstNewBossesNames = new ArrayList<>();
		
		if(listData != null) {
			listData.forEach(element -> {
				if(!lstNewBossesNames.contains(element.getJefeJerarquico())) {
					lstNewBossesNames.add(element.getJefeJerarquico()); 
				} 
			});
		}
		
		
		List<BossDetailVO> lstNewBosses = new ArrayList<BossDetailVO>();
		if(lstNewBossesNames != null && !lstNewBossesNames.isEmpty()) {
			lstNewBossesNames.forEach(bossName -> {
				ReDetalleJefeEntity detalleJefeEntity = daoDetalleJefe.findByNombre(bossName);
				BossDetailVO boss = new BossDetailVO();
				if(detalleJefeEntity != null) {
					boss.setIdJefe(detalleJefeEntity.getIdJefe());
					boss.setNombre(detalleJefeEntity.getNombre());
					boss.setCorreo(detalleJefeEntity.getCorreo());
					boss.setCorreoCC(detalleJefeEntity.getCorreoCC());
					boss.setDepartamento(daoDepartamento.findById(detalleJefeEntity.getIdDepartamento()).get().getDepartamento());
					boss.setInRecertificacion(false);
					boss.setRecertificado(false);
					boss.setPeriodo("0000");
					
					if(!lstNewBosses.contains(boss)) {
						lstNewBosses.add(boss); 
					}
				} 
			});
		}	
		
		return lstNewBosses;
    }
	
	@PostMapping("/ciatProfiles")
	public List<CiatExcelVO> processCiatProfiles(@RequestParam(name="file", required = false) MultipartFile file) 
			throws IOException, ProfilesException{
	
		if(file == null)
			throw new ProfilesException("Debes enviar un archivo");
		
		List<CiatExcelVO> listData = new ArrayList<>();
		try {
			listData.addAll(this.readCiat.readExcelCiatToVo(file.getInputStream(), ExcelCiatSheet.CIAT));
			listData.addAll(this.readCiat.readExcelCiatToVo(file.getInputStream(), ExcelCiatSheet.CIATENLINEA));
			
			if(listData  != null && !listData.isEmpty()) {
				this.loadlayoutCiatService.insertUsersData(listData);
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listData;
    }
	
	@PostMapping("/telProfiles")
	public List<TelExcelVO> processTelProfiles(@RequestParam(name="file", required = false) MultipartFile file) 
			throws IOException, ProfilesException{
	
		if(file == null)
			throw new ProfilesException("Debes enviar un archivo");
		
		List<TelExcelVO> listData = null;
		try {
			listData = this.readTel.readExcelTelToVo(file.getInputStream(), ExcelTelSheet.HOJA1);
			
			if(listData  != null) {
				this.loadlayoutTelService.insertUsersData(listData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listData;
    }
	
}
