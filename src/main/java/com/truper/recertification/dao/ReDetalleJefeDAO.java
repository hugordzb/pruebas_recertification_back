package com.truper.recertification.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.truper.recertification.model.ReDetalleJefeEntity;
import com.truper.recertification.vo.answer.systems.AnswerVO;

/**
 * This class content methods to find data on RE_DETALLE_JEFE table
 * @author mgmolinae
 */
public interface ReDetalleJefeDAO extends JpaRepository<ReDetalleJefeEntity, String>{

	/**
	 * This method find data by idDepartment
	 * @param idDepartamento
	 * @return ReDetalleJefeEntity
	 */
	public ReDetalleJefeEntity findByIdDepartamento(Integer idDepartamento);
	
	/**
	 * This method find data by boss name 
	 * @param nombre
	 * @return ReDetalleJefeEntity
	 */
	public ReDetalleJefeEntity findByNombre(String nombre);

	@Query(value ="Select rcu.IDUSUARIO, rcu.CUENTASISTEMA, rps.PERFIL, rs.SISTEMA " + 
			"from RE_JERARQUIA rj INNER " +
			"JOIN RE_CUENTAS_USUARIO rcu on rj.IDUSUARIO = rcu.IDUSUARIO " + 
			"INNER JOIN RE_PERFIL_SISTEMA rps on rps.IDPERFIL = rcu.IDPERFIL " + 
			"INNER JOIN RE_SISTEMA rs on rs.IDSISTEMA = rps.IDSISTEMA " + 
			"where rj.IDJEFE = 'mgmolinae'", nativeQuery = true)
	public List<AnswerVO> query(String idJefe);
	
}
