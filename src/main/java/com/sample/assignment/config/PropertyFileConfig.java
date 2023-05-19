/**
* Configuration for Property File.
* @author  Silvia Rose Simon
* @version 1.0 
*/
package com.sample.assignment.config;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyFileConfig {
	
	static Logger logger = Logger.getLogger(PropertyFileConfig.class); 
	public static Properties readPropertyFileFromClasspath() {
		Properties prop = new Properties();
		try {
			prop.load(PropertyFileConfig.class.getClassLoader()
					.getResourceAsStream("application.properties"));
		} catch (IOException e) {
			logger.error("Exception Encountered: "+e);
		}
		return prop;
	}

}
