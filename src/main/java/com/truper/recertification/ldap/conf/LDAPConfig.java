package com.truper.recertification.ldap.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;

/**
 * LDAP Configuration
 * @author mgmolinae
 */
@Configuration
@EnableLdapRepositories
public class LDAPConfig{
		
	/**
	 * This method make a merge with application.properties 
	 * and ignoreResults on LdapTemplate
	 * @param contextSource
	 * @return ldapTemplate
	 */
    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) {
    	LdapTemplate ldapTemplate = new LdapTemplate(contextSource);
    	ldapTemplate.setIgnorePartialResultException(true);
        return ldapTemplate;
    }
}