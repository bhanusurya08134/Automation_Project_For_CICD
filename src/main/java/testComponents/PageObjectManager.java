package testComponents;

import org.openqa.selenium.WebDriver;

import pageObjects.HomePage;

public class PageObjectManager {

	
	public WebDriver driver;
	
	public PageObjectManager(WebDriver driver) {
		this.driver = driver;
	}
	
	public HomePage hp;
	
	public HomePage getHomePage() {
		hp = new HomePage(driver);
		return hp;
	}
}
