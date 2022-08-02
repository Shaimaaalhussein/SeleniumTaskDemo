package pageClasses;

import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/************************Base class for common actions*********************************/

abstract class BasePage {

	
		protected WebDriver driver;
	    private WebDriverWait wait;
        
		//Constructor
		protected BasePage(WebDriver driver)  {
			this.driver = driver;
			wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		}


		//Wait  Method for visibility
				private void waitVisibility(By elementBy)  {
					
					wait.until(ExpectedConditions.elementToBeClickable(elementBy));
					
				}		
		
		
		//Click Method
				protected void click (By elementBy, String logText) {
			
			
			waitVisibility(elementBy);
				WebElement element = driver.findElement(elementBy);
				element.click();
			
			
		}
				//click by java script
				
				protected void clickByJs (By elementBy, String logText) {
					
					
					waitVisibility(elementBy);
					WebElement m=driver.findElement(elementBy);
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", m);
					
				}
				//Displayed element
				protected boolean elementDisplayed (By elementBy, String logText) {
			
			
			    waitVisibility(elementBy);
				WebElement element = driver.findElement(elementBy);
			try {	element.isDisplayed();
			return true;
			
			}
			catch (NoSuchElementException e) {
				return false;
			}
			
			
		}
		
				protected void switchTab() {
					ArrayList<String> newTb = new ArrayList<String>(driver.getWindowHandles());
				      //switch to new tab
				      driver.switchTo().window(newTb.get(1));
				}
		
		
	
	//Scroll to element
				protected void scrollByElement(By elementBy) {
					WebElement element=driver.findElement(elementBy);
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
				}
				
				//wait
				protected void waitSec() {
					driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				}
	}


