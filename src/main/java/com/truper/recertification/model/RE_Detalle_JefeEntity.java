package com.truper.recertification.model;

import java.io.Serializable;

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
@Table(name = "RE_DETALLE_JEFE")
public class RE_Detalle_JefeEntity implements Serializable, Persistable<String>{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDJEFE")
	private String idJefe;

	@Column(name = "NOMBRE")
	private String nombre;
	
	@Column(name = "CORREO")
	private String correo;

	@Column(name = "CORREOCC")
	private String correoCC;
	
	@Column(name = "IDDEPARTAMENTO")
	private Integer idDepartamento;

	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public String getId() {
		return this.getIdJefe();
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}

}