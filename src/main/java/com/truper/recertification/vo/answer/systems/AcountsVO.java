package com.truper.recertification.vo.answer.systems;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcountsVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String cCiat;
	
	private String pCiat;
	
	private String cSap;
	
	private String pSap;
	
	private String cTel;
	
	private String pTel;
}
