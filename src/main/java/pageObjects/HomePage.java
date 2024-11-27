package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;

	public HomePage(WebDriver driver) {
//		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public void launchApplication() throws InterruptedException {
				
		driver.get("https://www.google.co.in/");
		Thread.sleep(3000);
	}

}
