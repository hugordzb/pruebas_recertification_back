package com.truper.recertification.model;

import java.io.Serializable;
import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "RE_RECERTIFICACION")
public class RE_RecertificacionEntity implements Serializable, Persistable<String>{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "IDJEFE")
	private String idJefe;
	
	@Column(name = "PERIODO")
	private String periodo;
	
	@Column(name = "CARTASOLICITUD")
	private String cartaSolicitud;
	
	@Column(name = "CARTAAPROBADA")
	private String cartaAprobada;
	
	@Column(name = "ESTATUS")
	private boolean estatus;
	
	@Column(name = "ATENDIO")
	private String atendio;
	
	@Column(name = "COMENTARIOS")
	private String comentarios;
	
	@Column(name = "FECHATERMINO")
	private Timestamp fechaTermino;
	
	@Override
	public String getId() {
		return this.getIdJefe();
	}

	@Override
	public boolean isNew() {
		return this.isEstatus();
	}
	
}