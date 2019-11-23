package com.truper.recertification.vo.answer.systems;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountEmployeeVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String empleado;
		
	private List<AcountsVO> cuentas;
}
