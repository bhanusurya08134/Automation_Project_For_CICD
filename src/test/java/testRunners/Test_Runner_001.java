package testRunners;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/java/features",tags= "@tag1",glue = "stepDefinitions", monochrome = true,plugin = { "html:target/cucumber.html","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class Test_Runner_001 extends AbstractTestNGCucumberTests{
	

	@BeforeClass
	public void beforeClass() {
		System.out.println("Test Runner Executing . . .");
	}

}
