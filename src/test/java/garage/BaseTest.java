package garage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
	 private static final Logger logger = LogManager.getLogger(BaseTest.class); 
	protected void logMessage(String message) {
		logger.info(message);
	}
}
