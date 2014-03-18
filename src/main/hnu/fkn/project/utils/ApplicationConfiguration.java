package hnu.fkn.project.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguration {

	private ApplicationConfiguration() {
	}
	
	private static class AppPropertiesHolder { 
		//singleton implementation based on static initialization
		public static Properties PROPS = new Properties();
		
		static {
			InputStream is = ApplicationConfiguration.class.getClassLoader().getResourceAsStream("application.properties");
			try {
				PROPS.load(is);
			} catch (IOException e) {
				throw new ExceptionInInitializerError("Failed to load properties");
			}
		}
	}
	
	public static String getItem(String key) {
		return (String) AppPropertiesHolder.PROPS.get(key);
	}

}
