package com.truper.recertification.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Datos del usuario para la navegación")
public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

	@ApiModelProperty(notes = "Usuario que se logeo")
	private String userId;
	
	@ApiModelProperty(notes = "Token generado con JWT")
    private String token;
	
}
