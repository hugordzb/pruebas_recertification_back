package com.truper.recertification.excel.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.excel.vo.CiatLineaExcelVO;
import com.truper.recertification.excel.vo.EmailBossExcelVO;
import com.truper.recertification.excel.vo.NewFileExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.excel.vo.SapApoExcelVO;
import com.truper.recertification.excel.vo.SapExcelVO;
import com.truper.recertification.excel.vo.SapProfilesExcelVO;
import com.truper.recertification.excel.vo.TelExcelVO;

/**
 * This class map excel docs
 * @author mgmolinae
 */
@Component
public class RecertificacionExcelMapper {

	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
	
	/**
	 * This method map the principal excel doc of recertification
	 * @param rowData
	 * @return
	 * @throws ParseException
	 */
	public RecertificationExcelVO excelMapperRecert(List<String> rowData) {
		int i = 0;
		
		RecertificationExcelVO recertificacion = new RecertificationExcelVO();
		try {
			recertificacion.setNoEmpleado(Integer.parseInt(rowData.get(i++)));	
		} catch (Exception e) {
			recertificacion.setNoEmpleado(-1);
		}
		
		try {
			recertificacion.setFechaIngreso(sdf.parse(rowData.get(i++)));	
		} catch (Exception e) {
			
		}
		
		recertificacion.setNombre(rowData.get(i++));
		recertificacion.setUbicacion(rowData.get(i++));
		recertificacion.setDireccion(rowData.get(i++));
	    recertificacion.setArea(rowData.get(i++));
	    recertificacion.setDepartamento(rowData.get(i++));
	    recertificacion.setPuesto(rowData.get(i++));
	    recertificacion.setJefeJerarquico(rowData.get(i++));
	    recertificacion.setCorreoJefe(rowData.get(i++));
	    recertificacion.setNombreJefeFuncional(rowData.get(i++));
	    recertificacion.setCarta(rowData.get(i++));
	    recertificacion.setRespuesta(rowData.get(i++));
	    recertificacion.setAd(rowData.get(i++));
	    recertificacion.setSap(rowData.get(i++));
	    recertificacion.setTel(rowData.get(i++));
	    recertificacion.setCiat(rowData.get(i++));
	    recertificacion.setAltas(rowData.get(i++));
	    recertificacion.setBajas(rowData.get(i++));
	    recertificacion.setCambioCuenta(rowData.get(i++));
	    recertificacion.setCambioUsuario(rowData.get(i++));
	    recertificacion.setTicket(rowData.get(i++));
		recertificacion.setCarta(rowData.get(i++));
		recertificacion.setActivo(rowData.get(i++));
		recertificacion.setAtendio(rowData.get(i));
	
		return recertificacion;
	}
	
	/**
	 * This method map the profiles of tel, excel doc that need the recertification data
	 * @param rowData
	 * @return
	 * @throws ParseException
	 */
	public List<TelExcelVO> excelMapperTel(List<List<String>> rowData) throws ParseException {
			List<TelExcelVO> lstExcel = new ArrayList<>();
			
			for(int i = 0; i < rowData.size(); i++) {
				TelExcelVO tel = new TelExcelVO();
				
				tel.setEstatus(rowData.get(i).get(0));
				tel.setDepartamento(rowData.get(i).get(1));
				tel.setJefeJerarquico(rowData.get(i).get(2));
				tel.setUsuarioTel(rowData.get(i).get(3));
				tel.setRol(rowData.get(i).get(4));
				
				lstExcel.add(tel);
			}
			return lstExcel;
	}

	/**
	 * This method map the profiles of Sap, excel doc that need the recertification data
	 * @param rowData
	 * @return
	 * @throws ParseException
	 */
	public List<SapExcelVO> excelMapperSap(List<List<String>> rowData) throws ParseException {
		List<SapExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			SapExcelVO sap = new SapExcelVO();
			
			sap.setNombre(rowData.get(i).get(0));
			sap.setDepartamento(rowData.get(i).get(1));
			sap.setUsuarioSAP(rowData.get(i).get(2));
			sap.setRol(rowData.get(i).get(3));
			
			lstExcel.add(sap);
		}
		return lstExcel;
	}
	
	public List<CiatExcelVO> excelMapperCiat(List<List<String>> rowData) throws ParseException {
		List<CiatExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			CiatExcelVO ciat = new CiatExcelVO();
			
			ciat.setUsuario(rowData.get(i).get(0));
			ciat.setNombre(rowData.get(i).get(1));
			ciat.setPerfil(Integer.parseInt(rowData.get(i).get(2)));
			ciat.setFechaBaja(sdf.parse(rowData.get(i).get(3)));
			ciat.setAlmacen(rowData.get(i).get(4));
			
			lstExcel.add(ciat);
		}
		return lstExcel;
	}
	
	public List<CiatLineaExcelVO> excelMapperCiatLinea(List<List<String>> rowData) throws ParseException {
		List<CiatLineaExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			CiatLineaExcelVO ciatLinea = new CiatLineaExcelVO();
			
			ciatLinea.setUsuario(rowData.get(i).get(0));
			ciatLinea.setNombre(rowData.get(i).get(1));
			ciatLinea.setPerfil(Integer.parseInt(rowData.get(i).get(2)));
			ciatLinea.setNombrePerfil(rowData.get(i).get(3));
			ciatLinea.setCdrs(rowData.get(i).get(4));
			ciatLinea.setEstado(rowData.get(i).get(5));
			
			lstExcel.add(ciatLinea);
		}
		return lstExcel;
	}
	
	public List<SapProfilesExcelVO> excelMapperSapProfiles(List<List<String>> rowData) throws ParseException {
		List<SapProfilesExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			SapProfilesExcelVO sapProfile = new SapProfilesExcelVO();
			
			sapProfile.setUsuarios(rowData.get(i).get(0));
			sapProfile.setNombre(rowData.get(i).get(1));
			sapProfile.setPerfil(rowData.get(i).get(2));
			sapProfile.setTexto(rowData.get(i).get(3));
			
			lstExcel.add(sapProfile);
		}
		return lstExcel;
	}
	
	public List<SapApoExcelVO> excelMapperSapAPO(List<List<String>> rowData) throws ParseException {
		List<SapApoExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			SapApoExcelVO sapAPO = new SapApoExcelVO();
			
			sapAPO.setUsuarios(rowData.get(i).get(0));
			sapAPO.setNombre(rowData.get(i).get(1));
			sapAPO.setPerfil(rowData.get(i).get(2));
			sapAPO.setTexto(rowData.get(i).get(3));
			
			lstExcel.add(sapAPO);
		}
		return lstExcel;
	}
	
	public List<EmailBossExcelVO> excelMapperCorreo(List<List<String>> rowData) throws ParseException {
		List<EmailBossExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			EmailBossExcelVO correo = new EmailBossExcelVO();
			
			correo.setIdJefe(rowData.get(i).get(0));
			correo.setNombre(rowData.get(i).get(1));
			
			lstExcel.add(correo);
		}
		return lstExcel;
	}
	
	public List<NewFileExcelVO> excelMapperNewFormat(List<List<String>> rowData) throws ParseException {
		List<NewFileExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			NewFileExcelVO newFile = new NewFileExcelVO();
			
			newFile.setIdJefe(rowData.get(i).get(0));
			newFile.setNombreJefe(rowData.get(i).get(1));
			newFile.setIdEmpleado(rowData.get(i).get(2));
			newFile.setNombreEmpleado(rowData.get(i).get(3));
			newFile.setCuentatel(rowData.get(i).get(4));
			newFile.setRolTel(rowData.get(i).get(5));
			newFile.setCuentaCiat(rowData.get(i).get(6));
			newFile.setRolCiat(rowData.get(i).get(7));
			newFile.setCuentaSap(rowData.get(i).get(8));
			newFile.setRolSap(rowData.get(i).get(9));
			newFile.setDescRolSap(rowData.get(i).get(10));
			newFile.setDepartamento(rowData.get(i).get(11));
			
			lstExcel.add(newFile);
		}
		return lstExcel;
	}
}
