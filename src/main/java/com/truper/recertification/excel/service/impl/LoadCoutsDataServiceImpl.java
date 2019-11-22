package com.truper.recertification.excel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.excel.service.LoadCoutsDataService;
import com.truper.recertification.excel.vo.CorreoJefeExcelVO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDepartamentoEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReUsuarioEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoadCoutsDataServiceImpl implements LoadCoutsDataService{

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
	
	public void insertProfiles(String strPerfil, String strIdSystem) {
		try {
			if(daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSystem, strPerfil, null) == null){
				daoPerfil.save(RePerfilSistemaEntity
						.builder()
						.idSistema(strIdSystem)
						.perfil(strPerfil)
						.build());
				log.info("perfiles");
			}
		} catch (Exception e) {
			log.error("Error al insertar el perfil");
			log.info(e.getMessage());
		}
	}
	
	public void insertDepartamento(RecertificationExcelVO excelVO){
		try {
			if(daoDepa.findByDepartamento(excelVO.getDepartamento()) == null) {
				daoDepa.save(ReDepartamentoEntity
						.builder()
						.departamento(excelVO.getDepartamento())
						.build());
			}
					
		} catch (Exception e) {
			log.error("Ya existe el departamento");
			log.info(e.getMessage());
		}
	}
	
	public void insertDetalleJefe(CorreoJefeExcelVO correoJefeVO, RecertificationExcelVO excelVO) {
		try {
			if(daoDepa.findByDepartamento(excelVO.getDepartamento()).getIdDepartamento() == null) {
				daoJefe.save(ReDetalleJefeEntity.builder()
						.idJefe(correoJefeVO.getIdJefe())
						.idDepartamento(daoDepa.findByDepartamento(excelVO.getDepartamento()).getIdDepartamento())
						.nombre(excelVO.getJefeJerarquico())
						.correo(correoJefeVO.getIdJefe() + "@truper.com")
						.build());
			}
		} catch (Exception e) {
			log.error("Ya existe el Jefe");
			log.info(e.getMessage());
		}
	}
	
	public void insertUsuario(RecertificationExcelVO excelVO) {
		try {
			String strNoEmploy =excelVO.getNoEmpleado();
			if(strNoEmploy == null) {
				strNoEmploy = "0";
			}
			log.info("Usuario (empleado)");
			daoUsuario.save(ReUsuarioEntity.builder()
					.idUsuario(excelVO.getAd())
					.nombre(excelVO.getNombre())
					.noEmpleado(Integer.parseInt(strNoEmploy))
					.estatus(true)
					.build());
		} catch (Exception e) {
			log.error("ya existe el usuario en la tabla principal");
			log.info(e.getMessage());
		}
	}
	
	public void insertUsuarioJefe(String strAD, String strName) {
		try {
			log.info("Usuario (jefe)");
			daoUsuario.save(ReUsuarioEntity.builder()
					.idUsuario(strAD)
					.nombre(strName)
					.estatus(true)
					.build());
		} catch (Exception e) {
			log.error("ya existe el usuario en la tabla principal");
			log.info(e.getMessage());
		}
	}

	
	public void insertJerarquia(RecertificationExcelVO excelVO) {
		try {
			log.info("Jerarquia");
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
	
	public void insertAcount(String strIdUsuario, int intIdPerfil, String strCuenta) {
		try {
			log.info("Cuenta: " +strCuenta);
			daoCuentas.save(ReCuentasUsuarioEntity.builder()
					.idCuentaUsuario(PKCuentasUsuario.builder()
							.idUsuario(strIdUsuario)
							.idPerfil(intIdPerfil)
							.cuentaSistema(strCuenta)
							.build())
					.build());
		} catch (Exception e) {
			log.error("ya existe la cuenta " + strCuenta+" asociada al usuario " + strIdUsuario);
			log.info(e.getMessage());
		}
	}
}