package com.truper.recertification.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truper.recertification.dao.ReCuentasUsuarioDAO;
import com.truper.recertification.dao.RePerfilSistemaDAO;
import com.truper.recertification.model.PKCuentasUsuario;
import com.truper.recertification.model.ReCuentasUsuarioEntity;
import com.truper.recertification.model.RePerfilSistemaEntity;
import com.truper.recertification.model.ReUsuarioEntity;
import com.truper.recertification.service.DetailEmployeeService;
import com.truper.recertification.util.FiltersUtils;
import com.truper.recertification.utils.constants.SistemaCatalogs;
import com.truper.recertification.vo.answer.DetailCountsEmployeeVO;
import com.truper.recertification.vo.answer.systems.AccountDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DetailEmployeeServiceImpl implements DetailEmployeeService{

	@Autowired
	private ReCuentasUsuarioDAO daoCuentas;
	
	@Autowired
	private RePerfilSistemaDAO daoPerfil;
	
	@Override
	public DetailCountsEmployeeVO findEmployeeDetail(ReUsuarioEntity userEntity) {
		DetailCountsEmployeeVO employeeVO = new DetailCountsEmployeeVO();
		
		if(userEntity != null && userEntity.isEstatus()) {
			List<ReCuentasUsuarioEntity> lstCuenta = this.daoCuentas.findByIdCuentaUsuarioIdUsuario(userEntity.getIdUsuario());
			try {
				employeeVO.setIdEmpleado(userEntity.getIdUsuario());
				employeeVO.setEmpleado(userEntity.getNombre());
				
				List<AccountDataVO> listaCuentas = new ArrayList<>();
				
				for(int j = 0; j<lstCuenta.size(); j++) {
					this.findEmployeeAcounts(lstCuenta.get(j), listaCuentas);
				}
					
				employeeVO.setCuentas(listaCuentas);
			} catch (Exception e) {
				log.error("El usuario " + userEntity.getIdUsuario());
				log.info(e.getMessage());
			}
		}
		return employeeVO;
	}
	
	@Override
	public void findEmployeeAcounts(ReCuentasUsuarioEntity cuentasUsuario, List<AccountDataVO> lstTel) {
		AccountDataVO accVO = new AccountDataVO();
		
		PKCuentasUsuario pkUsuario = cuentasUsuario.getIdCuentaUsuario();
		
		int intIdPerfil = pkUsuario.getIdPerfil();
		Optional<RePerfilSistemaEntity> perfil = this.daoPerfil.findById(intIdPerfil);
		
		String strSistema = "";
		String strPerfil = "";
		
		if(perfil.isPresent()) {
			strSistema = perfil.get().getIdSistema();
			strPerfil = perfil.get().getPerfil();
		}
		
		SistemaCatalogs sistema = FiltersUtils.getCatalog(strSistema);
		
		String strCuenta = sistema.getSystemName();
		String cuentaSistema = pkUsuario.getCuentaSistema();
		
		accVO.setSystem(strCuenta);
		accVO.setCuenta(cuentaSistema);
		accVO.setPerfil(strPerfil);
		lstTel.add(accVO);
	}

}
