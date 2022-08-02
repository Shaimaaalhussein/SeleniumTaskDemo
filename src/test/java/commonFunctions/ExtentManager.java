package commonFunctions;
import java.io.File;


import com.aventstack.extentreports.ExtentReports;

import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	private static ExtentReports extent;
	static ConfigFileReader configFileReader = new ConfigFileReader();
	
	
	public static ExtentReports getInstance() {

		
		String FileName = "Release"+".html";
		String directory = configFileReader.getReportFilePath();
		new File(directory).mkdirs();
		String FullPath = directory + FileName;
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(FullPath);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setDocumentTitle("Automation Reports");
		htmlReporter.config().setReportName("Automation Test Results");
		htmlReporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		
		htmlReporter.setAppendExisting(false);
		
		extent.attachReporter(htmlReporter);
		

		return extent;
	}
}