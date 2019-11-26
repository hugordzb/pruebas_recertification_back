package com.truper.recertification.excel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.excel.service.LoadLayoutCiatService;
import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.exception.ProfilesException;
import com.truper.recertification.exception.RecertificationException;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoadLayoutCiatServiceImpl implements LoadLayoutCiatService{
	
	@Autowired
	private RePerfilSistemaDAO daoProfile;
	
	@Autowired
	private ReCuentasUsuarioDAO daoAcounts;
	
	@Override
	public List<RePerfilSistemaEntity> insertUsersData(List<CiatExcelVO> listData) 
			throws ProfilesException {

		List<RePerfilSistemaEntity> profileList = this.daoProfile.findByIdSistema("S001");

		if(listData == null || listData.isEmpty())
			throw new ProfilesException("No hay datos para insertar");
		
		Set<String> profilesUniques = listData.stream()
				.map(v -> v.getNombrePerfil().toUpperCase())
				.collect(Collectors.toSet());
		
		if(profilesUniques != null && !profilesUniques.isEmpty()) {
			profilesUniques.forEach( profile -> {
				Long count = profileList.stream()
						.filter( v -> v.getPerfil().equalsIgnoreCase(profile.trim()))
						.count();
				if(0 < count) {
					log.error("El perfil '" + profile.toUpperCase() + "' ya se encuentra asociado con el sistema");
				}else {
					RePerfilSistemaEntity profileTmp = this.daoProfile.save(
							RePerfilSistemaEntity.builder()
							.idSistema("S001")
							.perfil(profile.trim())
							.build());
					
					profileList.add(profileTmp);
				}
			});
		}
		
		//filtramos por perfil y usuario
		List<ReCuentasUsuarioEntity> acountsList = new ArrayList<>();
		List<RePerfilSistemaEntity> newProfileList = new ArrayList<>();
		
		listData.forEach(v ->{
			PKCuentasUsuario pk = new PKCuentasUsuario();
			pk.setCuentaSistema(v.getUsuario());
			log.info("cuenta: " + v.getUsuario());
			pk.setIdPerfil(300);
			
			ReCuentasUsuarioEntity acountEntity  = daoAcounts.
					findByIdCuentaUsuarioIdPerfilAndIdCuentaUsuarioCuentaSistema(
							300, v.getUsuario());
			if(acountEntity != null) {
				acountsList.add(acountEntity);
				
				profileList.forEach(profile ->{
					if(profile.getPerfil().equals(v.getNombrePerfil())) {
						
						newProfileList.add(profile);
						
					}else {
						log.info("No existe un perfil '" + v.getNombrePerfil() + "'");
					}
				});
			}
			else {
				log.error("No se encontro relacion registro de '" + v.getUsuario() + "'");
			}
		});
		
		log.info("lista: " + newProfileList.size());
		log.info("lista2: " + acountsList.size());
		
		//en esta insertamos
		acountsList.forEach(acount ->{ 
			
			PKCuentasUsuario pkAcounst = new PKCuentasUsuario();
			pkAcounst.setIdUsuario(acount.getIdCuentaUsuario().getIdUsuario());
//			pkAcounst.setIdPerfil();
//			
//			ReCuentasUsuarioEntity acountsTmp = this.daoAcounts.save(
//					ReCuentasUsuarioEntity.builder()
//					.idCuentaUsuario(pkAcounst)
//					.build());
			
		});
		
		return null;
	}

}
