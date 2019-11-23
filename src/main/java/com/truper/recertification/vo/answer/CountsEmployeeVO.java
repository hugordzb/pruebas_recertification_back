package com.truper.recertification.vo.answer;

import java.io.Serializable;
import java.util.List;

import com.truper.recertification.vo.answer.systems.AcountsVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.jasperreports.engine.JRDataSource;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountsEmployeeVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	
	private String sapAccounts;
	
	private String sapRoles;
	
	private String telAccounts;
	
	private String telRoles;
	
	private String ciatAccounts;
	
	private String ciatProfiles;
}
