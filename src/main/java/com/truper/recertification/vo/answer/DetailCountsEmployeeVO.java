package com.truper.recertification.vo.answer;

import java.io.Serializable;
import java.util.List;

import com.truper.recertification.vo.answer.systems.AccountDataVO;
import com.truper.recertification.vo.answer.systems.AcountsVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailCountsEmployeeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idEmpleado;
	
	private String empleado;
		
	private List<AccountDataVO> cuentas;
}
