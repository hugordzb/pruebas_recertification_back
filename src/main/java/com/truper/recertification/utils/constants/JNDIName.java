package com.truper.recertification.utils.constants;

public enum JNDIName {

	Recert("Recert");
	
	private String jndi;
	
	private JNDIName(String jndi) {
		this.jndi = jndi;
	}
	
	@Override 
    public String toString(){ 
		return this.jndi.trim().toUpperCase();
	}
}