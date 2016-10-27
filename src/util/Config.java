package util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class Config {

	private Properties config;
	public Config() throws IOException{
		String path = System.getProperty("user.dir");
		
		Reader reader = new FileReader(path + File.separator + "config.properties");
		config = new Properties();
		config.load( reader );
	}
	
	public String getProperty( String key ){
		return config.getProperty(key, "");
	}
	public String getProperty( String key, String defaultVaule ){
		
		String value = config.getProperty(key, "");
		return (value.isEmpty() ? defaultVaule : value);
	}
}
