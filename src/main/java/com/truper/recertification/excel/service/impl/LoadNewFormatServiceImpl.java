package com.truper.recertification.excel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.excel.service.LoadNewFormatService;
import com.truper.recertification.excel.vo.CorreoJefeExcelVO;
import com.truper.recertification.excel.vo.NewFileExcelVO;
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
public class LoadNewFormatServiceImpl implements LoadNewFormatService{

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
	
	public void insertDepartamento(NewFileExcelVO newFileVO){
		try {
			if(daoDepa.findByDepartamento(newFileVO.getDepartamento()) == null) {
				daoDepa.save(ReDepartamentoEntity
						.builder()
						.departamento(newFileVO.getDepartamento())
						.build());
			}
					
		} catch (Exception e) {
			log.error("Ya existe el departamento");
			log.info(e.getMessage());
		}
	}
	
	public void insertDetalleJefe(NewFileExcelVO newFileVO) {
		try {
			if(daoDepa.findByDepartamento(newFileVO.getDepartamento()).getIdDepartamento() == null) {
				daoJefe.save(ReDetalleJefeEntity.builder()
						.idJefe(newFileVO.getIdJefe())
						.idDepartamento(daoDepa.findByDepartamento(newFileVO.getDepartamento()).getIdDepartamento())
						.nombre(newFileVO.getNombreJefe())
						.correo(newFileVO.getIdJefe() + "@truper.com")
						.build());
			}
		} catch (Exception e) {
			log.error("Ya existe el Jefe");
			log.info(e.getMessage());
		}
	}
	
	public void insertUsuario(NewFileExcelVO newFileVO) {
		try {
			log.info("Usuario (empleado)");
			daoUsuario.save(ReUsuarioEntity.builder()
					.idUsuario(newFileVO.getIdEmpleado())
					.nombre(newFileVO.getNombreEmpleado())
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
	
	public void insertJerarquia(NewFileExcelVO newFileVO) {
		try {
			log.info("Jerarquia");
			daoJerarquia.save(ReJerarquiaEntity.builder()
					.idEmpleadoJefe(PKJerarquia.builder()
									.idUsuario(newFileVO.getIdEmpleado())
									.idJefe(daoJefe.findByNombre(newFileVO.getIdJefe()).getIdJefe())
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
