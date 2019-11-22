package com.truper.recertification.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.excel.service.LoadCoutsDataService;
import com.truper.recertification.vo.excel.RecertificationDocsVO;
import com.truper.recertification.vo.excel.RecertificationExcelVO;
import com.truper.recertification.vo.excel.SapApoExcelVO;
import com.truper.recertification.vo.excel.SapProfilesVO;

@Component
public class InsertExcelData {

	@Autowired
	private LoadCoutsDataService loadService;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Autowired
	private ReSistemaDAO daoSistema;

	public void insertDataLastRecertification(RecertificationDocsVO recertDocs) {
		
		this.insertPerfilSystem(recertDocs);
		this.insertBossData(recertDocs);
		
		for(int i = 0; i < recertDocs.getLstRecert().size(); i++) {
			RecertificationExcelVO recertVO =recertDocs.getLstRecert().get(i);
			loadService.insertUsuario(recertVO);
			loadService.insertJerarquia(recertVO);
			this.insertCuentasUsuario(recertDocs, recertVO);
		}
	}
	
	/**
	 * Read and insert profiles on SAP, TEL and CIAT 
	 * @param recertDocs
	 */
	public void insertPerfilSystem(RecertificationDocsVO recertDocs) {		
		String strIdSystemCiat = daoSistema.findBySistema("CIAT").getIdSistema();
		String strIdSystemSap = daoSistema.findBySistema("SAP").getIdSistema();
		String strIdSystemTel = daoSistema.findBySistema("TEL").getIdSistema();
		
		for(int i = 0; i < recertDocs.getLstCiat().size(); i++) {
			loadService.insertProfiles(recertDocs.getLstCiat().get(i).getNombrePerfil(),
					strIdSystemCiat);
		}
		
		for(int i = 0; i < recertDocs.getLstCiatLinea().size(); i++) {
			loadService.insertProfiles(recertDocs.getLstCiatLinea().get(i).getNombrePerfil(), 
					strIdSystemCiat);
		}
		
		for(int i = 0; i < recertDocs.getLstSapAPO().size(); i++) {
			SapApoExcelVO sapApoVO = recertDocs.getLstSapAPO().get(i);
			loadService.insertProfiles(sapApoVO.getPerfil() + sapApoVO.getNombre(), 
					strIdSystemSap);
		}
		
		for(int i = 0; i < recertDocs.getLstSapProfiles().size(); i++) {
			SapProfilesVO sapProfilesVO = recertDocs.getLstSapProfiles().get(i);
			loadService.insertProfiles(sapProfilesVO.getPerfil() + sapProfilesVO.getNombre(), 
					strIdSystemSap);
		}
		
		for(int i = 0; i < recertDocs.getLstTel().size(); i++) {
			loadService.insertProfiles(recertDocs.getLstTel().get(i).getRol(), strIdSystemTel);
		}
		
	}
	
	/**
	 * Read and insert boss data 
	 * @param recertDocs
	 */
	public void insertBossData(RecertificationDocsVO recertDocs) {
		for(int i = 0; i < recertDocs.getLstRecert().size(); i++) {
			RecertificationExcelVO recertVO =recertDocs.getLstRecert().get(i);
			
			loadService.insertDepartamento(recertVO);
			loadService.insertDetalleJefe(recertDocs.getLstCorreoJefe().get(i), recertDocs.getLstRecert().get(i));
			loadService.insertUsuarioJefe(recertDocs.getLstCorreoJefe().get(i).getIdJefe(), 
					recertDocs.getLstRecert().get(i).getNombreJefeFuncional());
		}
	}
	
	/**
	 * Read and insert Employee Acounts
	 * @param recertDocs
	 * @param excelVO
	 */
	public void insertCuentasUsuario(RecertificationDocsVO recertDocs, RecertificationExcelVO excelVO) {
		String strTel = excelVO.getTel();
		String strCiat = excelVO.getCiat();
		String strSAP = excelVO.getSap();
		
		String strIdSystemCiat = daoSistema.findBySistema("CIAT").getIdSistema();
		String strIdSystemSap = daoSistema.findBySistema("SAP").getIdSistema();
		String strIdSystemTel = daoSistema.findBySistema("TEL").getIdSistema();
		
		if(strTel != null && !strTel.isEmpty()) {
			for(int i = 0; i < recertDocs.getLstTel().size(); i++) {
				int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemTel, 
						recertDocs.getLstTel().get(i).getRol(), null).getIdPerfil();
				loadService.insertAcount(excelVO.getAd(), intIdPerfil, strTel);
			}
		}
		
		if(strCiat != null && !strCiat.isEmpty()) {
			for(int i = 0; i < recertDocs.getLstCiat().size(); i++) {
				int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemCiat, 
						recertDocs.getLstCiat().get(i).getNombrePerfil(), null).getIdPerfil();
				loadService.insertAcount(excelVO.getAd(), intIdPerfil, strCiat);
			}
			
			for(int i = 0; i < recertDocs.getLstCiatLinea().size(); i++) {
				int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemCiat, 
						recertDocs.getLstCiatLinea().get(i).getNombrePerfil(), null).getIdPerfil();
				loadService.insertAcount(excelVO.getAd(), intIdPerfil, strCiat);
			}
		}
		
		if(strSAP != null && !strSAP.isEmpty()) {
			for(int i = 0; i < recertDocs.getLstSapAPO().size(); i++) {
				int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemSap, 
						recertDocs.getLstSapAPO().get(i).getPerfil(), null).getIdPerfil();
				loadService.insertAcount(excelVO.getAd(), intIdPerfil, strSAP);
			}
			
			for(int i = 0; i < recertDocs.getLstSapProfiles().size(); i++) {
				int intIdPerfil = daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystemSap, 
						recertDocs.getLstSapProfiles().get(i).getPerfil(), null).getIdPerfil();
				loadService.insertAcount(excelVO.getAd(), intIdPerfil, strSAP);
			}
		}
	}	
}