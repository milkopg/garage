package com.development.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.development.log4j.LoggerManager;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private static final Logger logger = LoggerManager.getSystemLogger();
	private static final Logger logger2 = LoggerFactory.getLogger(AppInitializer.class);
	
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
		logger.trace("returns {}", servletLeMappingArray);
		logger2.trace("returns {}", servletLeMappingArray);
		
		return servletLeMappingArray;
	}

}
