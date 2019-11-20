package com.truper.recertification.vo.answer.sistemas;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcountsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cCiat;
	
	private String pCiat;
	
	private String cSap;
	
	private String pSap;
	
	private String cTel;
	
	private String pTel;
}
