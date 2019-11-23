package com.truper.recertification.vo.answer;

import java.io.Serializable;
import java.util.List;

import com.truper.recertification.vo.answer.systems.AcountsVO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountsEmployeeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String idEmpleado;
	
	private String empleado;
		
	private List<AcountsVO> cuentas;
}
