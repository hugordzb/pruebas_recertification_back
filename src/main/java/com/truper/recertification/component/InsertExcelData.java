package com.truper.recertification.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.PKPerfilSistema;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDepartamentoEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.vo.excel.RecertificationExcelVO;
import com.truper.recertification.vo.excel.TelExcelVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InsertExcelData {

	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;
	
	@Autowired
	private ReDepartamentoDAO daoDepa;
	
	@Autowired
	private ReDetalleJefeDAO daoJefe;
	
	@Autowired
	private ReJerarquiaDAO daoJerarquia;
	
	@Autowired
	private ReUsuarioDAO daoUsuario;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;

	public void insertDataLastRecertification(List<RecertificationExcelVO> list) {

		for(int i = 0; i < list.size(); i++) {
			
			this.insertDepartamento(list.get(i));
			this.insertDetalleJefe(list.get(i));
			this.insertUsuario(list.get(i));
			this.insertJerarquia(list.get(i));
//			this.insertCuentasUsuario(list.get(i));
			
		}
	}
	
	private void insertDepartamento(RecertificationExcelVO excelVO) {
		
		String strDepa = excelVO.getDepartamento();
		log.info("depa: " + strDepa);
		if(daoDepa.findByDepartamento(strDepa) == null){
		
			daoDepa.save(ReDepartamentoEntity.builder()
					.departamento(strDepa)
					.build());
		}else {
			log.info("ya existe el depa");
		}
	}
	
	private void insertDetalleJefe(RecertificationExcelVO excelVO) {
		
		String strJefe = excelVO.getNombreJefeFuncional();
		
		if(daoJefe.findByNombre(strJefe) == null){
			daoJefe.save(ReDetalleJefeEntity.builder()
					.idJefe("zara")
					.nombre(strJefe)
					.build());
		}else {
			log.info("ya existe el Jefe");
		}
	}
	
	private void insertUsuario(RecertificationExcelVO excelVO) {
		
		String strIdUsuario = excelVO.getAd();
		
		if(daoUsuario.findById(strIdUsuario) == null){
			daoUsuario.save(ReUsuarioEntity.builder()
					.idUsuario(strIdUsuario)
					.nombre(excelVO.getNombre())
					.noEmpleado(excelVO.getNoEmpleado())
					.estatus(true)
					.fechaIngreso(excelVO.getFechaIngreso())
					.build());
		}else {
			log.info("ya existe el usuario en la tabla principal");
		}
	}
	
	private void insertJerarquia(RecertificationExcelVO excelVO) {
		
		String strIdEmpleado = excelVO.getAd();
		String strIdJefe = daoJefe.findByNombre(excelVO.getJefeJerarquico()).getIdJefe();
		PKJerarquia pkJerarquia = new PKJerarquia(strIdJefe, strIdEmpleado);
		
		if(daoJerarquia.findById(pkJerarquia) == null){
			daoJerarquia.save(ReJerarquiaEntity.builder()
					.idEmpleadoJefe(PKJerarquia.builder()
							.idUsuario(strIdEmpleado)
							.idJefe(strIdJefe)
							.build())
					.build());
		}else {
			log.info("ya existe la relacion del Jefe-Empleado");
		}
	}

	
	private void insertCuentasUsuario(RecertificationExcelVO excelVO) {
		TelExcelVO telVo;
		String strTel = excelVO.getTel();
		String strCiat = excelVO.getCiat();
		String strSAP = excelVO.getSap();
		
		if(strTel != null) {

//			PKPerfilSistema pkPerfil = daoPerfil.findByPerfilAndIdPerfilSistemaIdSistema(strTel, "S001").getIdPerfilSistema();
//			
//			ReCuentasUsuarioEntity strCuentaExistente = daoCuentas.findById(new PKCuentasUsuario(
//					excelVO.getAd(), pkPerfil.getIdPerfil(), "S001", 
////					telVo.getUsuarioTel()
//					"rs_bcavazos"
//					)).get();
//			
//			if(!strCuentaExistente.getIdCuentaUsuario().getCuentaSistema().equals(strTel)) {
//				daoCuentas.save(ReCuentasUsuarioEntity.builder()
//						.idCuentaUsuario(PKCuentasUsuario.builder()
//								.idUsuario(excelVO.getAd())
//								.idPerfil(pkPerfil.getIdPerfil())
//								.idSistema("S001")
//								.cuentaSistema(strTel)
//								.build())
//						.build());
//			}else {
//				log.info("ya existe el usuario en tel");
//			}
			log.info("tel");
		}
		
		if(strCiat != null) {
			List<ReCuentasUsuarioEntity> s = daoCuentas.findByIdCuentaUsuarioCuentaSistema(strCiat);
			log.info("CIAT: " + s.size());
			
			if(s == null || s.isEmpty()) {
				daoCuentas.save(ReCuentasUsuarioEntity.builder()
					.idCuentaUsuario(PKCuentasUsuario.builder()
							.idUsuario(excelVO.getAd())
							.idPerfil(5)
							.idSistema("S002")
							.cuentaSistema(strCiat)
							.build())
					.build());
			}else {
				log.info("ya existe el usuario en ciat");
			}
		}
		
		if(strSAP != null) {
			List<ReCuentasUsuarioEntity> s = daoCuentas.findByIdCuentaUsuarioCuentaSistema(strSAP);
			log.info("SAP: " + s.size());
			if(s == null || s.isEmpty()) {
				daoCuentas.save(ReCuentasUsuarioEntity.builder()
						.idCuentaUsuario(PKCuentasUsuario.builder()
								.idUsuario(excelVO.getAd())
								.idPerfil(6)
								.idSistema("S003")
								.cuentaSistema(strSAP)
								.build())
						.build());
			}else {
				log.info("ya existe el usuario en SAP");
			}
		}
	}
}