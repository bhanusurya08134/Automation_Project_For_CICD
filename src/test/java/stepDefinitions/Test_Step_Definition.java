package stepDefinitions;

import java.util.Map;

import io.cucumber.java.en.Given;
import testComponents.BaseTest;
import testComponents.ExcelSheetHandler;
import testComponents.TestSetup;

public class Test_Step_Definition extends BaseTest{
	
	TestSetup testSetup;

	
	public Test_Step_Definition(TestSetup testSetup) {
		this.testSetup = testSetup;
	}

	@Given("I launch application")
	public void i_launch_application() {
		try {
			test = logInfo.get().createNode("I launch application");
			
			Map<String, String> testData = ExcelSheetHandler.getDataInMap("testdata","WFM_01");
			System.out.println(testData);
			
			testSetup.pageObjectManager.getHomePage().launchApplication();
			
			reportScreenshot(testSetup.driver);
			reportPass("Title", "Expected", "Actual");
			reportFail("Title", "Expected", "Actual");
			
			
			System.out.println("Step definition executing . . .");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Given("I close Browser")
	public void i_close_browser() {
		try {
		test = logInfo.get().createNode("I close Browser");
		
		testSetup.driver.close();
		testSetup.driver.quit();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
