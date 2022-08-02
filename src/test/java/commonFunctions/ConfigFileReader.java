package commonFunctions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	private Properties properties;
	private final String propertyFilePath = "configuration//Configuration.properties";
	private String basePath =System.getProperty("user.dir");

	public ConfigFileReader() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}
	}
	
	/****Get browser type******/
	public String getBrowserType() {
		String browserType = properties.getProperty("browserType");
		if (browserType != null)
			return browserType;
		else
			throw new RuntimeException("BrowserType not specified in the Configuration.properties file.");
	}
	
	/****Get URL******/
	public String getURL() {
		String fileName = properties.getProperty("URL");
		if (fileName != null)
			return fileName;
		else
			throw new RuntimeException("URL not specified in the Configuration.properties file.");
	}
	
	
	/****make new file path for report ******/
	public String getReportFilePath() {
		String filePath = properties.getProperty("reportfilePath");
		
	
		if (filePath != null)
			return basePath+filePath;
		else
			throw new RuntimeException("reportFilePath not specified in the Configuration.properties file.");
	}
	
	/****make new file path for screenshot ******/
	public String getScreenShotFilePath() {
		String filePath =properties.getProperty("screenshotfilePath");
		if (filePath != null)
			return basePath+filePath;
		else
			throw new RuntimeException("screenshotfilePath not specified in the Configuration.properties file.");
	}
}
