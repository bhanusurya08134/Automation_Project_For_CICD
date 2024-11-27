package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import testComponents.BaseTest;

public class Hooks extends BaseTest{
	
	
	public Hooks() {
		super();
	}


	@Before
	public void testConfigSetUp(Scenario scenario){
		
		System.out.println("Hooks File executing . . .");
		
		this.scenario = scenario;
		test = extent.createTest(scenario.getName());
		logInfo.set(test);
		
		String []module =scenario.getName().split(" ");
	    test.assignCategory(module[0]);
	}
	
	
	@After
	public void tearDown(Scenario scenario) {
		
	}
	

}
