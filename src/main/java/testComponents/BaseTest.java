package testComponents;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.*;
import java.time.Duration;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.cucumber.java.Scenario;

public class BaseTest {

	public BaseTest() {
		super();
	}
	
	public static String folderName;
	
	public static String typeOfTest = "Regression";
	public static ExtentSparkReporter report = null;
	public static ExtentReports extent = null;
	public static ExtentTest test;
	public static Scenario scenario;
	public static ThreadLocal<ExtentTest> logInfo = new ThreadLocal<ExtentTest>();
	
	public WebDriver driver;
	
	public static SoftAssert softAssert;
	
	
	
	
	public ExtentReports setUp() {
		folderName = typeOfTest+"_"+setFolderName();
		String reportLocation = System.getProperty("user.dir") + "/"  +"AutomationReports"+"/"+folderName+ "/" + "ExtentReport" + ".html";
		report = new ExtentSparkReporter(reportLocation);
		report.config().setEncoding("utf-8");
		System.out.println("Report Location Intialized . . .");
		
		extent = new ExtentReports();
		extent.attachReporter(report);
		extent.setSystemInfo("Application", "Test Web");
		
		return extent;
	}
	
	

	public WebDriver initializeDriver() throws MalformedURLException {
		if(driver == null) {
			ChromeOptions opt = new ChromeOptions();
			
			opt.addArguments("--disable-gpu");
			opt.addArguments("--disable-dev-shm-usage");
			opt.addArguments("--remote-allow-origins=*");
			
//			opt.addArguments("--headless=new");
			
			opt.setAcceptInsecureCerts(true);
			
			URL url = new URL("http://localhost:4444/wd/hub");
			
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "Downloads");
			opt.setExperimentalOption("prefs", prefs);
			
			driver = new ChromeDriver(opt);
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
			
			driver.manage().window().maximize();
			
			return driver;
		}
		
		return driver;
	}

	public void reportScreenshot(WebDriver driver) throws IOException {

		test.info("<b>" + "<font color=" + "orange>" + "Screenshot" + "</b>",
				MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(driver)).build());
	}
	

	public String captureScreenShot(WebDriver driver) throws IOException {
		
		System.out.println("Folder Name : "+folderName);
		
		TakesScreenshot screen = (TakesScreenshot) driver;
		File src = screen.getScreenshotAs(OutputType.FILE);
//		String dest = System.getProperty("user.dir") + "\\"+"AutomationReports"+"\\"+folderName+ "\\" +"ScreenShots"+"\\"+ getcurrentdateandtime() + ".jpg";
		String dest = System.getProperty("user.dir") + "/"+"AutomationReports"+"/"+folderName+ "/" +"ScreenShots"+"/"+ getcurrentdateandtime() + ".jpg";
		File target = new File(dest);
		FileUtils.copyFile(src, target);
		return dest;
	}
	
	public void reportInfo(String msg) {
		test.pass(msg);
	}

	public void reportPass(String title, String exp, String act) {
		String message = "<b>" + "Expected " + title + " : " + "</b>" + "<font color=" + "green>" + exp + "</font>"
				+ "\t" + "<b>" + "Actual " + title + " : " + "</b>" + "<font color=" + "green>" + act + "</font>";
		test.pass(message);
	}

	public void reportFail(String title, String exp, String act) {
		String message = "<b>" + "Expected " + title + " : " + "</b>" + "<font color=" + "red>" + exp + "</font>" + "\t"
				+ "<b>" + "Actual " + title + " : " + "</b>" + "<font color=" + "red>" + act + "</font>";
		test.fail(message);
		softAssert.assertEquals(exp, act);
//		softAssertThreadLocal.get().assertEquals(act, exp);
	}
	
	
	
	
	
	
	
	public static String setFolderName() {
		Locale locale = new Locale("en", "UK");
		DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(locale);
		String pattern = "d_MMM_YY HH_mm_ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, dateFormatSymbols);

		String timestamp = simpleDateFormat.format(new Date());
		return timestamp;
	}
	
	private static String getcurrentdateandtime() {
		String str = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str = dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		} catch (Exception e) {
		}
		return str;
	}

}
