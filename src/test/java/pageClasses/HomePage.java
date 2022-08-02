package pageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/************************home page for elements and actions*********************************/


public class HomePage extends BasePage {


	public HomePage(WebDriver driver) {
		super(driver);
	}
	By country=By.xpath("//span[text()='Egypt']");		
	By city=By.xpath("//span[text()='Cairo']");	
	By category=By.xpath("(//*[@class='product-title bold-font text-left'])[2]");
	By product=By.xpath("(//*[contains(@href,'id=84695')])[1]");
	By addToCart=By.xpath("//*[@class='add-to-cart btn btn-floward m-0 w-100']");
	By contBtn=By.xpath("//*[text()=' Continue ']");
	By itemCards=By.xpath("//*[@class='col-3 col-md-3 px-1 img-prod']");

	
	
	public void selectCountry() {
		click(country, "Select country");
	}
	public void selectCity() {
		click(city, "Select city");
	}

	
	public void selectCategory() {
		
		click(category, "Select category");
	}
public void selectProduct() {
		
		click(product, "Select product");
	}


public void addToCart() {
	switchTab();
	
	click(addToCart, "add to cart");
	click(contBtn, "continue");
}
public boolean itemAdded() {
	return elementDisplayed(itemCards, "item added to cart");
	
}

}
