package testComponents;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

public class TestSetup {

	public BaseTest baseTest;
	
	public WebDriver driver;
	
	public PageObjectManager pageObjectManager;

	public TestSetup() throws MalformedURLException {
		baseTest = new BaseTest();
		driver = baseTest.initializeDriver();
		
		pageObjectManager = new PageObjectManager(driver);
		
		
		System.out.println("TestSetup executing . . .");
	}
	
	
}
