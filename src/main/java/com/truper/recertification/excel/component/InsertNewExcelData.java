package com.truper.recertification.excel.component;

import org.springframework.beans.factory.annotation.Autowired;

import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.excel.recertification.vo.RecertificationDocsVO;
import com.truper.recertification.excel.service.LoadCoutsDataService;
import com.truper.recertification.excel.service.LoadNewFormatService;
import com.truper.recertification.excel.vo.NewFileExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.excel.vo.SapApoExcelVO;
import com.truper.recertification.excel.vo.SapProfilesExcelVO;

public class InsertNewExcelData {

	@Autowired
	private LoadNewFormatService loadNewService;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Autowired
	private ReSistemaDAO daoSistema;

	public void insertDataLastRecertification(RecertificationDocsVO recertDocs) {
		
		for(int i = 0; i < recertDocs.getLstNewFile().size(); i++) {
			NewFileExcelVO newFileVO =recertDocs.getLstNewFile().get(i);
			
			this.insertPerfilSystem(newFileVO);
			this.insertBossData(newFileVO);
			loadNewService.insertUsuario(newFileVO);
			loadNewService.insertJerarquia(newFileVO);
			this.insertCuentasUsuario(newFileVO);
		}
	}

	/**
	 * Read and insert profiles on SAP, TEL and CIAT 
	 * @param newFileVO
	 */
	public void insertPerfilSystem(NewFileExcelVO newFileVO) {		
		String strIdSystemCiat = daoSistema.findBySistema("CIAT").getIdSistema();
		String strIdSystemSap = daoSistema.findBySistema("SAP").getIdSistema();
		String strIdSystemTel = daoSistema.findBySistema("TEL").getIdSistema();
		
		String strRolCiat = newFileVO.getRolCiat();
		String strRolSap = newFileVO.getRolSap() + newFileVO.getDescRolSap();
		String strRolTel = newFileVO.getRolTel();
		
		if(!strRolCiat.isEmpty() && strRolCiat != null) {
			loadNewService.insertProfiles(strRolCiat, strIdSystemCiat);
		}
		if(!strRolSap.isEmpty() && strRolSap != null) {
			loadNewService.insertProfiles(strRolSap, strIdSystemSap);			
		}
		if(!strRolTel.isEmpty() && strRolTel != null) {
			loadNewService.insertProfiles(strRolTel, strIdSystemTel);
		}			
	}
	
	/**
	 * Read and insert boss data 
	 * @param newFileVO
	 */
	public void insertBossData(NewFileExcelVO newFileVO) {
		
		loadNewService.insertDepartamento(newFileVO);
		loadNewService.insertDetalleJefe(newFileVO);
		loadNewService.insertUsuarioJefe(newFileVO.getIdJefe(), newFileVO.getNombreJefe());
	}
	
	/**
	 * Read and insert Employee Acounts
	 * @param recertDocs
	 * @param excelVO
	 */
	public void insertCuentasUsuario(NewFileExcelVO newFileVO) {
		String strTel = newFileVO.getCuentatel();
		String strCiat = newFileVO.getCuentaCiat();
		String strSap = newFileVO.getCuentaSap();
		
		String strIdSystemCiat = daoSistema.findBySistema("CIAT").getIdSistema();
		String strIdSystemSap = daoSistema.findBySistema("SAP").getIdSistema();
		String strIdSystemTel = daoSistema.findBySistema("TEL").getIdSistema();
		
		if(strTel != null && !strTel.isEmpty()) {
			
			int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemTel, 
					newFileVO.getRolTel(), null).getIdPerfil();
			
			loadNewService.insertAcount(newFileVO.getIdEmpleado(), intIdPerfil, strTel);
			
		}
		
		if(strCiat != null && !strCiat.isEmpty()) {
			int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemCiat, 
					newFileVO.getRolCiat(), null).getIdPerfil();
			
			loadNewService.insertAcount(newFileVO.getIdEmpleado(), intIdPerfil, strCiat);
		}
		
		if(strSap != null && !strSap.isEmpty()) {
			int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemSap, 
					newFileVO.getRolSap() + newFileVO.getDescRolSap(), null).getIdPerfil();
			
			loadNewService.insertAcount(newFileVO.getIdEmpleado(), intIdPerfil, strSap);
		}
	}	

}
