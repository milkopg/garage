package com.development.log4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.general.Constants;

public class LoggerManager {
	/**
	 * get instance of system logs i.e. full logs, exceptions, warns, etc
	 * @return
	 */
	public static Logger getSystemLogger() {
		return LoggerFactory.getLogger(Constants.LOGGER_SYSTEM);
	}
	
	/**
	 * get instance of user logs - user info messages
	 * @return
	 */
	public static Logger getUserLogger() {
		return LoggerFactory.getLogger(Constants.LOGGER_USER);
	}
}
