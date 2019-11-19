package com.truper.recertification;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Empleado implements Serializable{

	private static final long serialVersionUID = 1L;

    private int id;

    private String nombre;

    private String empresa;
 
    
}