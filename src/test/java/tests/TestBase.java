package tests;

import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import commonFunctions.ConfigFileReader;
import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class TestBase {
	public WebDriver driver = null;
    ConfigFileReader configFileReader;
  
	
	@BeforeMethod
	public void setup(Method m) throws InterruptedException {
		System.out.println("TestBase.setup()");
		configFileReader = new ConfigFileReader();
		String browserType=configFileReader.getBrowserType().toLowerCase().toLowerCase();
        switch(browserType) {
    	
    	case "chrome":
    		WebDriverManager.chromedriver().setup();
    	    driver= new ChromeDriver();
		break;
		
    	case "firefox":
    		WebDriverManager.firefoxdriver().setup();
    		driver=new FirefoxDriver();
    		
    	break;
    		
		default:
			break;
}
		  driver.navigate().to(configFileReader.getURL());
		
	}

	
	@AfterMethod
	void tearDown() {
	
		driver.quit();
	}
	@AfterClass
	public void afterClass() {
	
	}
}
