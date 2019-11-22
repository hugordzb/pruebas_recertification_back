package com.truper.recertification.excel.service;

import com.truper.recertification.vo.excel.CorreoJefeVO;
import com.truper.recertification.vo.excel.RecertificationExcelVO;

public interface LoadCoutsDataService {

	/**
	 * This method insert on RE_PERFIL_SISTEMA table
	 * @param strPerfil
	 * @param strIdSystem
	 */
	public void insertProfiles(String strPerfil, String strIdSystem);
	
	/**
	 * This method insert on RE_DEPARTAMENTO table
	 * @param excelVO
	 */
	public void insertDepartamento(RecertificationExcelVO excelVO);
	
	/**
	 * This method insert on RE_DETALLE_JEFE table
	 * @param correoJefeVO
	 * @param excelVO
	 */
	public void insertDetalleJefe(CorreoJefeVO correoJefeVO, RecertificationExcelVO excelVO);
	
	/**
	 * This method insert on RE_USUARIO tables the employees
	 * @param excelVO
	 */
	public void insertUsuario(RecertificationExcelVO excelVO);

	/**
	 * This method insert on RE_USUARIO tables the boss
	 * @param strAD
	 * @param strName
	 */
	public void insertUsuarioJefe(String strAD, String strName);

	/**
	 * This method insert on RE_JERARQUIA table the relation between boss and employee
	 * @param excelVO
	 */
	public void insertJerarquia(RecertificationExcelVO excelVO);
	
	/**
	 * This method insert on RE_CUENTAS_USUARIO table
	 * @param strIdUsuario
	 * @param intIdPerfil
	 * @param strCuenta
	 */
	public void insertAcount(String strIdUsuario, int intIdPerfil, String strCuenta);
	
}
