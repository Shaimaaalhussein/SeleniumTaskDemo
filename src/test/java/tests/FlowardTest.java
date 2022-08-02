package tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import pageClasses.HomePage;
import commonFunctions.JiraCreateIssue;

public class FlowardTest extends TestBase {

	
    HomePage homePg;
 
    String Actual;
    String Expected;
   
	@BeforeMethod
	
	void initUtlities() {
		
		System.out.println("initUtlities()");
		homePg=new HomePage(driver);
	
			
	}


	@JiraCreateIssue(isCreateIssue=true)
	@Test(description ="Add to cart", priority = 1)
	
	
	public void AddToCart() {
	
		homePg.selectCountry();
		homePg.selectCity();
		homePg.selectCategory();
		homePg.selectProduct();
		homePg.addToCart();
		Assert.assertEquals( homePg.itemAdded(), true,"item didn't add to cart");
		
	}
	

}
