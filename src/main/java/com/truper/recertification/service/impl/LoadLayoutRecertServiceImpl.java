package com.truper.recertification.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.ReDepartamentoDAO;
import com.truper.recertification.dao.ReDetalleJefeDAO;
import com.truper.recertification.dao.ReJerarquiaDAO;
import com.truper.recertification.dao.ReUsuarioDAO;
import com.truper.recertification.excel.vo.RecertificationExcelVO;
import com.truper.recertification.exception.RecertificationException;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.PKJerarquia;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.ReDepartamentoEntity;
import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.model.ReJerarquiaEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.service.LoadLayoutRecertService;
import com.truper.recertification.util.FiltersUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoadLayoutRecertServiceImpl implements LoadLayoutRecertService {
	
	@Autowired
	private ReUsuarioDAO usuarioDAO;
	
	@Autowired
	private ReCuentasUsuarioDAO cuentaDAO;
	
	@Autowired
	private ReDepartamentoDAO deptoDAO;
	
	@Autowired
	private ReDetalleJefeDAO jefeDAO;
	
	@Autowired
	private ReJerarquiaDAO jerarquiaDAO;
	
	@Override
	public List<ReUsuarioEntity> insertUsersData(List<RecertificationExcelVO> listData)
			throws RecertificationException {
		
		if(listData == null || listData.isEmpty())
			throw new RecertificationException("No hay datos para insertar");
		
		List<ReUsuarioEntity> usuariosList = new LinkedList<>();
		List<ReDepartamentoEntity> deptoList = this.deptoDAO.findAll();
		List<ReDetalleJefeEntity> jefesList = this.jefeDAO.findAll();
		
		Set<String> deptosUniques = listData.stream()
				.map(v -> v.getDepartamento().toUpperCase())
				.collect(Collectors.toSet());
		
		if(deptosUniques != null && !deptosUniques.isEmpty()) {
			deptosUniques.forEach( dpt -> {
				Long count = deptoList.stream()
						.filter( v -> v.getDepartamento().equalsIgnoreCase(dpt.trim()))
						.count();
				if(count > 0) {
					log.error("El Departamento '" + dpt.toUpperCase() + "' Ya existe en la DB");
				}else {
					ReDepartamentoEntity dptoTmp = this.deptoDAO.save(ReDepartamentoEntity.builder()
							.exist(false)
							.departamento(dpt.trim())
							.build());
					
					deptoList.add(dptoTmp);
				}
			});
		}
		
		List<ReUsuarioEntity> users = new LinkedList<>();
		log.info("Se insertaran " + listData.size() + " usuarios");
		
		listData.forEach( v -> {
			ReUsuarioEntity objUsuario = this.convertToReUsuarioEntity(v);
			
			if(objUsuario != null) {
				Optional<ReUsuarioEntity> objUserDB =  this.usuarioDAO.findById(objUsuario.getId());
				
				if(!objUserDB.isPresent()) {
					objUsuario.setExist(true);
				}else {
					objUsuario.setExist(false);
				}
				
				log.info("Insertando usuario " + objUsuario.getId() + "...");
				this.usuarioDAO.save(objUsuario);
				usuariosList.add(objUsuario);
				
				this.convertToReCuentasUsuarioEntity(v).forEach( rowCuenta -> {
					log.info("\tInsertando la cuenta " + rowCuenta.getId().getCuentaSistema() 
							+ " para " + rowCuenta.getId().getIdUsuario());
					this.cuentaDAO.save(rowCuenta);
				});
			}
		});
		
		log.info("Insertando los jefes por departamento");
		List<RecertificationExcelVO> jefesUniques = listData.stream()
				.filter(FiltersUtils.distinctByKey( p -> p.getJefeJerarquico()))
				.collect(Collectors.toList());
		
		if(jefesUniques != null && !jefesUniques.isEmpty()) {
			jefesUniques.forEach( jefe -> {
				Long count = jefesList.stream()
						.filter( v -> v.getNombre().trim().equalsIgnoreCase(jefe.getJefeJerarquico().trim()))
						.count();
				
				if(count > 0) {
					log.error("El JEFE '" + jefe.getJefeJerarquico().toUpperCase() + "' Ya existe en la DB");
				}else {
					ReDepartamentoEntity depto = deptoList.stream()
							.filter( v -> jefe.getDepartamento().equalsIgnoreCase(v.getDepartamento()))
							.findAny().orElse(null);
					
					ReDetalleJefeEntity jefeTmp = this.jefeDAO.save(
							ReDetalleJefeEntity.builder()
							.nombre(jefe.getJefeJerarquico())
							.idJefe(jefe.getCorreoJefe().replaceAll("@truper.com", ""))
							.correo(jefe.getCorreoJefe())
							.exist(false)
							.idDepartamento(depto.getIdDepartamento())
							.build());
					
					jefesList.add(jefeTmp);
				}
			});
		}
		
		log.info("Cargando Relacion Empleado -> Jefe");
		usuariosList.forEach( v -> {
			RecertificationExcelVO usuarioRow = listData.stream()
					.filter(row -> row.getAd().equalsIgnoreCase(v.getIdUsuario()))
					.findAny().orElse(null);
			
			if(usuarioRow != null) {
				Optional<ReJerarquiaEntity> jerExist = this.jerarquiaDAO.findById(
						new PKJerarquia(v.getIdUsuario(), 
								usuarioRow.getCorreoJefe().replaceAll("@truper.com", "")));
				
				if(!jerExist.isPresent()) {
					ReJerarquiaEntity jerarquia = new ReJerarquiaEntity();
					jerarquia.setIdEmpleadoJefe(new PKJerarquia(v.getIdUsuario(), usuarioRow.getCorreoJefe().replaceAll("@truper.com", "")));
					this.jerarquiaDAO.save(jerarquia);
				}else {
					log.warn("La relación de Usario " + v.getIdUsuario() + " y JEFE " + usuarioRow.getCorreoJefe() + " ya existe en la DB");
				}
			}
		});
		
		return users;
	}
	
	//SE DEBE CAMBIAR EL ID PERFIL 300 POR UNO QUE CONTENA 1 Ó 0
	private List<ReCuentasUsuarioEntity> convertToReCuentasUsuarioEntity(RecertificationExcelVO vo) {
		List<ReCuentasUsuarioEntity> listCuentas = new LinkedList<>();
		
		ReCuentasUsuarioEntity cuentaUser;
		if(vo.getSap() != null && !vo.getSap().isEmpty()) {
			cuentaUser = new ReCuentasUsuarioEntity();
			cuentaUser.setIdCuentaUsuario(new PKCuentasUsuario(vo.getAd(), 300, vo.getSap()));
			listCuentas.add(cuentaUser);
		}
		
		if(vo.getTel() != null && !vo.getTel().isEmpty()) {
			cuentaUser = new ReCuentasUsuarioEntity();
			cuentaUser.setIdCuentaUsuario(new PKCuentasUsuario(vo.getAd(), 300, vo.getTel()));
			listCuentas.add(cuentaUser);
		}
		
		if(vo.getCiat() != null && !vo.getCiat().isEmpty()) {
			cuentaUser = new ReCuentasUsuarioEntity();
			cuentaUser.setIdCuentaUsuario(new PKCuentasUsuario(vo.getAd(), 300, vo.getCiat()));
			listCuentas.add(cuentaUser);
		}
		
		return listCuentas;
	}
	
	private ReUsuarioEntity convertToReUsuarioEntity(RecertificationExcelVO vo) {
		ReUsuarioEntity entity = new ReUsuarioEntity();
		entity.setIdUsuario(vo.getAd());
		entity.setNombre(vo.getNombre());
		entity.setNoEmpleado(vo.getNoEmpleado());
		entity.setEstatus("1".equalsIgnoreCase(vo.getActivo()) ? true : false);
		entity.setFechaIngreso(vo.getFechaIngreso());
		return entity;
	}

}
