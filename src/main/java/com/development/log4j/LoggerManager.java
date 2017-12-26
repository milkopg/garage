package com.development.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.general.Constants;

public class LoggerManager {
	public static Logger getSystemLogger() {
		return LoggerFactory.getLogger(Constants.LOGGER_SYSTEM);
	}
	
	public static Logger getUserLogger() {
		return LoggerFactory.getLogger(Constants.LOGGER_USER);
	}
}
