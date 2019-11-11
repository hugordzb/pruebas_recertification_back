package com.truper.recertification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Table(name = "RE_SSO_SISTEMA")
public class RE_UsuarioEntity implements Serializable, Persistable<String>{

	private static final long serialVersionUID = 1L;

	@Column(name = "IDUSUARIO")
	private String idUsuario;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "NOEMPLEADO")
	private Integer noEmpleado;

	@Column(name = "ESTATUS")
	private boolean estatus;

	@Column(name = "FECHAINGRESO")
	private Date fechaIngreso;
	
	@Column(name = "FECHATERMINACION")
	private Date fechaTerminacion;

	@Override
	public String getId() {
		return this.getIdUsuario();
	}

	@Override
	public boolean isNew() {
		return this.isEstatus();
	}

}
