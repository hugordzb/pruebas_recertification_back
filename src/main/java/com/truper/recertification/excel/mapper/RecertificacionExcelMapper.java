package com.truper.recertification.excel.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
 * This class make a mapper between vo and excel docs
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
	public TelExcelVO excelMapperTel(List<String> rowData) {
		int i = 0;
		TelExcelVO tel = new TelExcelVO();
		
		tel.setEstatus(rowData.get(i++));
		tel.setDepartamento(rowData.get(i++));
		tel.setJefeJerarquico(rowData.get(i++));
		tel.setUsuarioTel(rowData.get(i++));
		tel.setRol(rowData.get(i));
			
		return tel;
	}

	/**
	 * This method map the profiles of Sap, excel doc that need the recertification data
	 * @param rowData
	 * @return
	 * @throws ParseException
	 */
	public SapExcelVO excelMapperSap(List<String> rowData) {
		SapExcelVO sap = new SapExcelVO();
		int i = 0;
		
		sap.setNombre(rowData.get(i++));
		sap.setDepartamento(rowData.get(i++));
		sap.setUsuarioSAP(rowData.get(i++));
		sap.setRol(rowData.get(i));
		
		return sap;
	}
	
	public CiatExcelVO excelMapperCiat(List<String> rowData) {
		CiatExcelVO ciat = new CiatExcelVO();
		int i = 0;
		
		ciat.setUsuario(rowData.get(i++));
		ciat.setNombre(rowData.get(i++));
		try {
			ciat.setPerfil(Integer.parseInt(rowData.get(i++)));
		} catch (Exception e) {
			ciat.setPerfil(-1);
		}
		ciat.setNombrePerfil(rowData.get(i++));
		try {
			ciat.setFechaBaja(sdf.parse(rowData.get(i++)));	
		} catch (Exception e) {
			
		}
		ciat.setAlmacen(rowData.get(i));
		
		return ciat;
	}
	
	public CiatLineaExcelVO excelMapperCiatLinea(List<String> rowData) {
		CiatLineaExcelVO ciatLinea = new CiatLineaExcelVO();
		int i = 0;
		
		ciatLinea.setUsuario(rowData.get(i++));
		ciatLinea.setNombre(rowData.get(i++));
		try {
			ciatLinea.setPerfil(Integer.parseInt(rowData.get(i++)));
		} catch (Exception e) {
			ciatLinea.setPerfil(-1);
		}
		
		ciatLinea.setNombrePerfil(rowData.get(i++));
		ciatLinea.setCdrs(rowData.get(i++));
		ciatLinea.setEstado(rowData.get(i));
		
		return ciatLinea;
	}
	
	public SapProfilesExcelVO excelMapperSapProfiles(List<String> rowData) {
		SapProfilesExcelVO sapProfile = new SapProfilesExcelVO();
		int i = 0;
		
		sapProfile.setUsuarios(rowData.get(i++));
		sapProfile.setNombre(rowData.get(i++));
		sapProfile.setPerfil(rowData.get(i++));
		sapProfile.setTexto(rowData.get(i));
		
		return sapProfile;
	}
	
	public SapApoExcelVO excelMapperSapAPO(List<String> rowData) {
		SapApoExcelVO sapAPO = new SapApoExcelVO();
		int i = 0;
		
		sapAPO.setUsuarios(rowData.get(i++));
		sapAPO.setNombre(rowData.get(i++));
		sapAPO.setPerfil(rowData.get(i++));
		sapAPO.setTexto(rowData.get(i));
		
		return sapAPO;
	}
	
	public EmailBossExcelVO excelMapperCorreo(List<String> rowData) {
		EmailBossExcelVO correo = new EmailBossExcelVO();
		int i = 0;
		
		correo.setIdJefe(rowData.get(i++));
		correo.setNombre(rowData.get(i));
		
		return correo;
	}
	
	public NewFileExcelVO excelMapperNewFormat(List<String> rowData) {
		NewFileExcelVO newFile = new NewFileExcelVO();
		int i = 0;
		
		newFile.setIdJefe(rowData.get(i++));
		newFile.setNombreJefe(rowData.get(i++));
		newFile.setIdEmpleado(rowData.get(i++));
		newFile.setNombreEmpleado(rowData.get(i++));
		newFile.setCuentatel(rowData.get(i++));
		newFile.setRolTel(rowData.get(i++));
		newFile.setCuentaCiat(rowData.get(i++));
		newFile.setRolCiat(rowData.get(i++));
		newFile.setCuentaSap(rowData.get(i++));
		newFile.setRolSap(rowData.get(i++));
		newFile.setDescRolSap(rowData.get(i++));
		newFile.setDepartamento(rowData.get(i++));

		return newFile;
	}
}
