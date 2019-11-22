package com.truper.recertification.excel.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.excel.vo.CiatLineaExcelVO;
import com.truper.recertification.excel.vo.CorreoJefeExcelVO;
import com.truper.recertification.excel.vo.NewFileExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.excel.vo.SapApoExcelVO;
import com.truper.recertification.excel.vo.SapExcelVO;
import com.truper.recertification.excel.vo.SapProfilesExcelVO;
import com.truper.recertification.excel.vo.TelExcelVO;

@Component
public class RecertificacionExcelMapper{

	public List<RecertificationExcelVO> excelMapperRecert(List<List<String>> rowData) throws ParseException {
		List<RecertificationExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			RecertificationExcelVO recertificacion = new RecertificationExcelVO();
		
			recertificacion.setNoEmpleado(rowData.get(i).get(0));
			recertificacion.setNombre(rowData.get(i).get(2));
			recertificacion.setUbicacion(rowData.get(i).get(3));
			recertificacion.setDireccion(rowData.get(i).get(4));
		    recertificacion.setArea(rowData.get(i).get(5));
		    recertificacion.setDepartamento(rowData.get(i).get(6));
		    recertificacion.setPuesto(rowData.get(i).get(7));
		    recertificacion.setJefeJerarquico(rowData.get(i).get(8));
		    recertificacion.setNombreJefeFuncional(rowData.get(i).get(9));
		    recertificacion.setCarta(rowData.get(i).get(10));
		    recertificacion.setRespuesta(rowData.get(i).get(11));
		    recertificacion.setAd(rowData.get(i).get(12));
		    recertificacion.setSap(rowData.get(i).get(13));
		    recertificacion.setTel(rowData.get(i).get(14));
		    recertificacion.setCiat(rowData.get(i).get(15));
		    recertificacion.setAltas(Boolean.parseBoolean(rowData.get(i).get(16)));
		    recertificacion.setBajas(Boolean.parseBoolean(rowData.get(i).get(17)));
		    recertificacion.setCambioCuenta(rowData.get(i).get(18));
		    recertificacion.setCambioUsuario(rowData.get(i).get(19));
		    recertificacion.setTicket(rowData.get(i).get(20));
			recertificacion.setCarta(rowData.get(i).get(21));
			recertificacion.setActivo(Boolean.parseBoolean(rowData.get(i).get(22)));
			recertificacion.setAtendio(rowData.get(i).get(23));
			
			lstExcel.add(recertificacion);
		}
		return lstExcel;
	}
	
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
	
	public List<CorreoJefeExcelVO> excelMapperCorreo(List<List<String>> rowData) throws ParseException {
		List<CorreoJefeExcelVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			CorreoJefeExcelVO correo = new CorreoJefeExcelVO();
			
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
