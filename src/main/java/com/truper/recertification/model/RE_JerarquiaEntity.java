package com.truper.recertification.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "RE_JERARQUIA")
public class RE_JerarquiaEntity implements Serializable, Persistable<PK_Jerarquia>{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PK_Jerarquia idEmpleadoJefe;
	
	@Transient
	@Builder.Default
	private boolean exist = false;
	
	@Override
	public PK_Jerarquia getId() {
		return this.getIdEmpleadoJefe();
	}

	@Override
	public boolean isNew() {
		return this.isExist();
	}
}
