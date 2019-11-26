package com.truper.recertification.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReBitacoraCambiosDAO;
import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.dao.ReSistemaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.json.request.vo.RequestChangeVO;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReBitacoraCambiosEntity;
import com.truper.recertification.model.ReControlCambiosEntity;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.service.ValidateAcountsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ValidateChangeAcountsServiceImpl implements ValidateAcountsService{

	@Autowired
	private ReBitacoraCambiosDAO daoCambios;
  
	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;
	
	@Autowired
	private ReDetalleJefeDAO daoJefe;
	
	@Autowired
	private ReJerarquiaDAO daoJerarquia;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Autowired 
	private ReSistemaDAO daoSistema;
	
	@Autowired
	private ReUsuarioDAO daoUsuario;
	
	@Override
	public String validateRequest (RequestChangeVO requestVO) {
		String strIdSistema = "";
		switch (requestVO.getTipoMov()) {
		case "A":
			try {
				strIdSistema = daoSistema.findBySistema(requestVO.getSistema()).getIdSistema();
				
				daoUsuario.findByIdUsuario(requestVO.getNIdUsuario());
				this.validateSystemProfile(strIdSistema, requestVO.getPerfil());
				daoJefe.findById(requestVO.getIdJefe());
				
			}catch (Exception e) {
				log.error("Datos no validos, favor de revisar");
				log.info(e.getMessage());
			}
			break;
		case "B":
			try {
				if(requestVO.getIdJefe() != null && requestVO.getIdUsuario() != null) {
					daoUsuario.findById(requestVO.getIdJefe());
				}else if (requestVO.getIdUsuario() != null && requestVO.getIdJefe() == null){
					daoUsuario.findById(requestVO.getIdUsuario());
				}else if(requestVO.getCuentaSistema() != null) {
					PKCuentasUsuario cuentas = new PKCuentasUsuario();
					
					strIdSistema = daoSistema.findBySistema(requestVO.getSistema()).getIdSistema();
					int intIdPerfil = this.validateSystemProfile(strIdSistema, requestVO.getPerfil());
					
					cuentas.setIdUsuario(requestVO.getIdUsuario());
					cuentas.setIdPerfil(intIdPerfil);
					cuentas.setIdUsuario(requestVO.getCuentaSistema());
					daoCuentas.findById(cuentas);
				}
			}catch (Exception e) {
				log.error("No se ha podido encontrar un registro valido para la baja, favor de validar");
				log.info(e.getMessage());
			}
			break;
		case "M":
			try {
				daoJefe.findById(requestVO.getIdJefe());
				daoUsuario.findByIdUsuarioAndEstatus(requestVO.getNIdJefe(), true);
			} catch (Exception e) {
				log.error("No se ha podido encontrar un registro con los datos a modificar, favor de validar");
				log.info(e.getMessage());
			}
			break;
		default: 
			log.error("El tipo de movimiento no es valido");
			break;
		}
		return strIdSistema;
	}
	
	@Override
	public String processRequest(ReBitacoraCambiosEntity bitacora) {
		String strIdSistema = daoSistema.findBySistema(bitacora.getSistema()).getIdSistema();

		try {
			switch (bitacora.getTipoMov()) {
			case "A":
				ReCuentasUsuarioEntity cuenta = new ReCuentasUsuarioEntity();
				PKCuentasUsuario pk = new PKCuentasUsuario();
				
				String strIdUsuario = bitacora.getIdUsuario();
				
				pk.setIdUsuario(strIdUsuario);
				pk.setIdPerfil(daoPerfil.findByIdSistemaAndPerfilAndRol(bitacora.getSistema(), bitacora.getNPerfil(), null).getIdPerfil());
				pk.setCuentaSistema(bitacora.getCuentaSistema());
				
				cuenta.setIdCuentaUsuario(pk);
				daoCuentas.save(cuenta);
				
				PKJerarquia pkJerarquia = new PKJerarquia(strIdUsuario, bitacora.getIdJefe());
				
				if(daoJerarquia.findById(pkJerarquia).get() == null) {
					daoJerarquia.save(ReJerarquiaEntity
							.builder()
							.idEmpleadoJefe(pkJerarquia)
							.build());
				}
				break;
			case "B":
				ReUsuarioEntity user = new ReUsuarioEntity();
				user.setEstatus(false);
				
				if(bitacora.getIdJefe() != null) {
					user.setIdUsuario(bitacora.getIdJefe());
					daoUsuario.save(user);
				}else if (bitacora.getIdUsuario() != null){
					user.setIdUsuario(bitacora.getIdUsuario());
					daoUsuario.save(user);
				}else if(bitacora.getCuentaSistema() != null) {
					PKCuentasUsuario cuentas = new PKCuentasUsuario();
					
					cuentas.setIdUsuario(bitacora.getIdUsuario());
					cuentas.setIdPerfil(daoPerfil.findByIdSistemaAndPerfilAndRol(bitacora.getSistema(), bitacora.getNPerfil(), null).getIdPerfil());
					cuentas.setIdUsuario(bitacora.getCuentaSistema());
					
					daoCuentas.deleteById(cuentas);
				}
				break;
			case "M":
				String strIdJefe = bitacora.getIdJefe();
				String strNIdBoss = bitacora.getNIdJefe();
				List<ReJerarquiaEntity> lstJerarquia = daoJerarquia.findByIdEmpleadoJefeIdJefe(strIdJefe);
				
				if(daoJefe.findById(strNIdBoss).get() == null) {
					daoJefe.save(ReDetalleJefeEntity
							.builder()
							.idJefe(strNIdBoss)
							.idDepartamento(daoJefe.findById(strIdJefe).get().getIdDepartamento())
							.correo(strNIdBoss+"@truper.com")
							.build());
				}
				
				for(int i = 0; i < lstJerarquia.size(); i++) {
					ReJerarquiaEntity jerarquia = new ReJerarquiaEntity();
					jerarquia.setIdEmpleadoJefe(
							new PKJerarquia(lstJerarquia.get(i).getId().getIdUsuario(), strNIdBoss));
					daoJerarquia.save(jerarquia);
				}
				break;
			default: 
				break;
			}
		}catch (Exception e) {
			log.error("Error al realizar cambios");
			log.info(e.getMessage());
		}
		return strIdSistema;
	}
	
	@Override
	public Integer mapRequest(RequestChangeVO requestVO, String strIdSistema)  throws Exception{
		try {
			Date dateChange = new Date();
			daoCambios.save(ReBitacoraCambiosEntity
					.builder()
					.tipoMov(requestVO.getTipoMov())
					.idUsuario(requestVO.getIdUsuario())
					.sistema(strIdSistema)
					.perfil(requestVO.getPerfil())
					.idJefe(requestVO.getIdJefe())
					.cuentaSistema(requestVO.getCuentaSistema())
					.nIdUsuario(requestVO.getNIdUsuario())
					.nSistema(requestVO.getNSistema())
					.nPerfil(requestVO.getNPerfil())
					.nIdJefe(requestVO.getNIdJefe())
					.nCuentaSistema(requestVO.getNCuentaSistema())
					.solicitante(requestVO.getSolicitante())
					.fechaSolicitud(new Timestamp(dateChange.getTime()))
					.build());
			return daoCambios.findAll().size();	
		}catch (Exception e) {
			log.error("Error al insertar, datos no validos");
			log.info(e.getMessage());
		}
		return null;
	}
	
	@Override
	public void generateControlChange(ReControlCambiosEntity control) {
			daoCambios.save(control);
	}
	
	@Override
	public Integer validateSystemProfile(String strIdSistema, String strPerfil) {
		return daoPerfil.findByIdSistemaAndPerfilAndRol(strIdSistema, strPerfil, null).getIdPerfil();
	}

}
