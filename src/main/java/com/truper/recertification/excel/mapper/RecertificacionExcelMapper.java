package com.truper.recertification.excel.mapper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.truper.recertification.vo.excel.CiatExcelVO;
import com.truper.recertification.vo.excel.CiatLineaExcelVO;
import com.truper.recertification.vo.excel.CorreoJefeVO;
import com.truper.recertification.vo.excel.RecertificationExcelVO;
import com.truper.recertification.vo.excel.SapApoExcelVO;
import com.truper.recertification.vo.excel.SapExcelVO;
import com.truper.recertification.vo.excel.SapProfilesVO;
import com.truper.recertification.vo.excel.TelExcelVO;

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
	
	public List<SapProfilesVO> excelMapperSapProfiles(List<List<String>> rowData) throws ParseException {
		List<SapProfilesVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			SapProfilesVO sapProfile = new SapProfilesVO();
			
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
	
	public List<CorreoJefeVO> excelMapperCorreo(List<List<String>> rowData) throws ParseException {
		List<CorreoJefeVO> lstExcel = new ArrayList<>();
		
		for(int i = 0; i < rowData.size(); i++) {
			CorreoJefeVO correo = new CorreoJefeVO();
			
			correo.setIdJefe(rowData.get(i).get(0));
			correo.setNombre(rowData.get(i).get(1));
			
			lstExcel.add(correo);
		}
		return lstExcel;
	}
}
