package com.truper.recertification.ldap.repository;

import org.springframework.data.ldap.repository.LdapRepository;

import com.truper.recertification.vo.LDAPVO;

/**
 * AD (LDAP) info to find specific data
 * @author mgmolinae
 */
public interface LDAPRepository extends LdapRepository<LDAPVO>{
	
	/**
	 * This method find by Username on LDAP
	 * @param username
	 * @return 
	 */
	public LDAPVO findByUsername(String username);
	
	/**
	 * This method find by id Employ on LDAP
	 * @param employ
	 * @return
	 */
	public LDAPVO findByEmploy(Integer employ);

	/**
	 * This method find by email on LDAP
	 * @param mail
	 * @return
	 */
	public LDAPVO findByMail(String mail);
}