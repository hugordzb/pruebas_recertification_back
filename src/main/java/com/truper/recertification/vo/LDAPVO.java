package com.truper.recertification.vo;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

/**
 * Minimum data that needs to be known from AD (LDAP) 
 * @author mgmolinae
 */
@Setter
@Getter
@Entry(base= "ou = Truper", objectClasses = {"organizationalPerson","person","user","top"})
public class LDAPVO{

	@JsonIgnore
	@Id
	private Name id;
	
	@Attribute(name="sAMAccountName")
	private String username;
	
	@Attribute(name="mail")
	private String mail;
	
	@Attribute(name="initials")
	private Integer employ;
	
	@Attribute(name="lockoutTime")
	private Integer block;
	
}