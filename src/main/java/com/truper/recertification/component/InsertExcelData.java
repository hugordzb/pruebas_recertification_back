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
			this.insertCuentasUsuario(list.get(i));
			
		}
	}
	
	private void insertDepartamento(RecertificationExcelVO excelVO) {
		try {
			daoDepa.save(ReDepartamentoEntity.builder()
					.departamento(excelVO.getDepartamento())
					.build());
		} catch (Exception e) {
			log.error("Ya existe el departamento");
			log.info(e.getMessage());
		}
	}
	
	private void insertDetalleJefe(RecertificationExcelVO excelVO) {
		try {
			daoJefe.save(ReDetalleJefeEntity.builder()
					.idJefe("ejemplo")
					.nombre(excelVO.getNombreJefeFuncional())
					.build());
		} catch (Exception e) {
			log.error("Ya existe el Jefe");
			log.info(e.getMessage());
		}
	}
	
	private void insertUsuario(RecertificationExcelVO excelVO) {
		try {
			daoUsuario.save(ReUsuarioEntity.builder()
					.idUsuario(excelVO.getAd())
					.nombre(excelVO.getNombre())
					.noEmpleado(excelVO.getNoEmpleado())
					.estatus(true)
					.fechaIngreso(excelVO.getFechaIngreso())
					.build());
		} catch (Exception e) {
			log.error("ya existe el usuario en la tabla principal");
			log.info(e.getMessage());
		}
	}
	
	private void insertJerarquia(RecertificationExcelVO excelVO) {
		try {
			daoJerarquia.save(ReJerarquiaEntity.builder()
					.idEmpleadoJefe(PKJerarquia.builder()
									.idUsuario(excelVO.getAd())
									.idJefe(daoJefe.findByNombre(excelVO.getJefeJerarquico()).getIdJefe())
									.build())
					.build());
		} catch (Exception e) {
			log.error("ya existe la relacion del Jefe-Empleado");
			log.info(e.getMessage());
		}
	}

	
	private void insertCuentasUsuario(RecertificationExcelVO excelVO) {
		String strTel = excelVO.getTel();
		String strCiat = excelVO.getCiat();
		String strSAP = excelVO.getSap();
		
		
		if(strTel != null) {
			try {
				daoCuentas.save(ReCuentasUsuarioEntity.builder()
						.idCuentaUsuario(PKCuentasUsuario.builder()
								.idUsuario(excelVO.getAd())
								.idPerfil(daoPerfil.findByPerfilAndIdPerfilSistemaIdSistema(strTel, "S001").getIdPerfilSistema().getIdPerfil())
								.idSistema("S001")
								.cuentaSistema(strTel)
								.build())
						.build());
			} catch (Exception e) {
				log.error("ya existe el usuario en tel");
				log.info(e.getMessage());
			}
		}
		
		if(strCiat != null) {
			try {
				daoCuentas.save(ReCuentasUsuarioEntity.builder()
						.idCuentaUsuario(PKCuentasUsuario.builder()
								.idUsuario(excelVO.getAd())
								.idPerfil(5)
								.idSistema("S002")
								.cuentaSistema(strCiat)
								.build())
						.build());
			} catch (Exception e) {
				log.error("ya existe el usuario en ciat");
				log.info(e.getMessage());
			}
		}
		
		if(strSAP != null) {
			try {
				daoCuentas.save(ReCuentasUsuarioEntity.builder()
						.idCuentaUsuario(PKCuentasUsuario.builder()
								.idUsuario(excelVO.getAd())
								.idPerfil(6)
								.idSistema("S003")
								.cuentaSistema(strSAP)
								.build())
						.build());
			} catch (Exception e) {
				log.error("Ya existe el usuario en SAP");
				log.info(e.getMessage());
			}
		}
	}
}