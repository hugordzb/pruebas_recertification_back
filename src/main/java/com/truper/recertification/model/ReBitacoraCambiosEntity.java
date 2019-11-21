package com.truper.recertification.model;

import java.io.Serializable;
import java.sql.Timestamp;

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

	@Column(name = "TIPOMOV")
	private String tipoMov;
	
	@Column(name = "IDUSUARIO")
	private String idUsuario;
	
	@Column(name = "SISTEMA")
	private String sistema;
	
	@Column(name = "PERFIL")
	private String perfil;
	
	@Column(name = "IDJEFE")
	private String idJefe;
	
	@Column(name = "CUENTASISTEMA")
	private String cuentaSistema;
	
	@Column(name = "NIDUSUARIO")
	private String nIdUsuario;
	
	@Column(name = "NSISTEMA")
	private String nSistema;
	
	@Column(name = "NPERFIL")
	private String nPerfil;

	@Column(name = "NIDJEFE")
	private String nIdJefe;
	
	@Column(name = "NCUENTASISTEMA")
	private String nCuentaSistema;
		
	@Column(name = "SOLICITANTE")
	private String solicitante;
	
	@Column(name = "FECHASOLICITUD")
	private Timestamp fechaSolicitud;
	
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