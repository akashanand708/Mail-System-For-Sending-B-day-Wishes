package com.birthdaywishes.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {
	private static Properties properties=new Properties();
	private static Logger log=Logger.getLogger(Config.class);
	static{
		try {
			FileInputStream inputStream=new FileInputStream("resources/config.properties");
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error(e);
		}catch(IOException e){
			log.error(e);
		}
	}
	
	/**
	 * This method reurns value corresponding to key from Config.properties file.
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
}
