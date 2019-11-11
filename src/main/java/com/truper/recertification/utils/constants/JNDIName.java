package com.truper.recertification.utils.constants;

public enum JNDIName {

	recertificationDS("recertificationDS");
	
	private String jndi;
	
	private JNDIName(String jndi) {
		this.jndi = jndi;
	}
	
	@Override 
    public String toString(){ 
		return this.jndi.trim().toUpperCase();
	}
}