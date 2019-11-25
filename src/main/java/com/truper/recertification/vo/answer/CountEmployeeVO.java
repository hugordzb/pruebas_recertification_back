package com.truper.recertification.vo.answer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CountEmployeeVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String nombre;
	
	private String ciatAccounts;
	
	private String ciatProfiles;
	
	private String sapAccounts;
	
	private String sapRoles;
	
	private String telAccounts;
	
	private String telRoles;
}
