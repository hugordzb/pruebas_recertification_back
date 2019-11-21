package com.truper.recertification.common.template;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.Setter;

@Service
public class MailContentBuilder {
	
	private TemplateEngine templateEngine;
	
	@Setter
	private String htmlTemplateName;
	
	private Context context;
	
	@PostConstruct
	public void init() {
		context = new Context();
	}
	
	/**
	 * Select name of Template to use
	 * @param templateEngine
	 */
    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    
    /**
     * Map parameters names of Template
     * @param strParametro
     * @param string
     */
    public void addParametro(String strParametro, String string) {
    	context.setVariable(strParametro, string);
    }
    
    /**
     * Map parameters names of Template
     * @param strParametro
     * @param object
     */
    public void addParametro(String strParametro, Object object) {
    	context.setVariable(strParametro, object);
    }
    
    /**
     * Build Template 
     * @return templateEngine
     */
    public String build() {
        return templateEngine.process(htmlTemplateName, context);
    }
}