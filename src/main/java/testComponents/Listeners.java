package testComponents;


import org.testng.*;

public class Listeners extends BaseTest implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {

	}

	@Override
	public void onTestSuccess(ITestResult result) {

	}

	@Override
	public void onTestFailure(ITestResult result) {

	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {
		System.out.println("OnStart Started . . .");
		extent = setUp();

	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("onFinish ended . . .");
		
		extent.flush();
	}

	
}
