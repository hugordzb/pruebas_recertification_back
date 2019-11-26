package com.truper.recertification.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "RE_Usuario")
public class ReUsuarioEntity implements Serializable, Persistable<String>{

	private static final long serialVersionUID = 1L;

	@Id
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

	@Transient
	private boolean exist;
	
	@Override
	public String getId() {
		return this.getIdUsuario();
	}

	@Override
	public boolean isNew() {
		return this.exist;
	}

}
