package com.truper.recertification.excel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.excel.service.LoadLayoutCiatService;
import com.truper.recertification.excel.vo.CiatExcelVO;
import com.truper.recertification.exception.ProfilesException;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.utils.constants.Constants;
import com.truper.recertification.utils.constants.SistemaCatalogs;

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

		List<RePerfilSistemaEntity> profileList = this.daoProfile.findByIdSistema(SistemaCatalogs.CIAT.getCode());

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
							.idSistema(SistemaCatalogs.CIAT.getCode())
							.perfil(profile.trim())
							.build());
					
					profileList.add(profileTmp);
				}
			});
		}
		
		//filtramos por perfil y usuario
		List<ReCuentasUsuarioEntity> acountsList = new ArrayList<>();
		
		listData.forEach(v ->{
			ReCuentasUsuarioEntity acountEntity  = this.daoAcounts.
					findByIdCuentaUsuarioIdPerfilAndIdCuentaUsuarioCuentaSistema(Constants.DEFAULT_PROFILE, v.getUsuario());
			
			if(acountEntity != null) {
				
				RePerfilSistemaEntity currentProfile = profileList.stream()
						.filter(perf -> v.getNombrePerfil().trim().equalsIgnoreCase(perf.getPerfil()))
						.findAny().orElse(null);
				
				if(currentProfile != null) {
					PKCuentasUsuario pkAccount = acountEntity.getIdCuentaUsuario();
					this.daoAcounts.deleteById(pkAccount);
					
					pkAccount.setIdPerfil(currentProfile.getIdPerfil());
					acountEntity.setIdCuentaUsuario(pkAccount);
					
					this.daoAcounts.save(acountEntity);
					acountsList.add(acountEntity);
				}
			}else {
				log.error("No se encontro relacion registro de '" + v.getUsuario() + "'");
			}
		});
		return profileList;
	}

}
