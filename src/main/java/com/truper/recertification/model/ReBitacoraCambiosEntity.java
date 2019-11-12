package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RE_BITACORA_CAMBIOS")
public class ReBitacoraCambiosEntity implements Serializable, Persistable<Integer>{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDMOVIMIENTO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMovimiento;

	@Column(name = "IDUSUARIO")
	private String idUsuario;
	
	@Column(name = "TIPOMOV")
	private String tipoMov;
	
	@Column(name = "IDPERFIL")
	private Integer idPerfil;
	
	@Column(name = "IDSISTEMA")
	private String idSistema;
	
	@Column(name = "CUENTAUSUARIO")
	private String cuentaSistema;
	
	@Column(name = "IDJEFE")
	private String idJefe;
	
	@Column(name = "NIDUSUARIO")
	private String nIdUsuario;
	
	@Column(name = "NIDPERFIL")
	private Integer nIdPerfil;
	
	@Column(name = "NIDSISTEMA")
	private String nIdSistema;
	
	@Column(name = "NCUENTAUSUARIO")
	private String nCuentaSistema;
	
	@Column(name = "NIDJEFE")
	private String nIdJefe;
		
	@Column(name = "SOLICITANTE")
	private String solicitante;
	
	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public Integer getId() {
		return this.getIdMovimiento();
	}

	@Override
	public boolean isNew() {
		return this.exist;
	}
	
}