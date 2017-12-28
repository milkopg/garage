package com.development.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	
	/**
	 * Defining AppConfig.class  
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}
 
	@Override
	protected Class<?>[] getServletConfigClasses() {
		logger.trace("returns null");
		return null;
	}
 
	/**
	 * Returns  servlet mapping
	 */
	@Override
	protected String[] getServletMappings() {
		String [] servletLeMappingArray = new String[] { "/" };
		return servletLeMappingArray;
	}
}
