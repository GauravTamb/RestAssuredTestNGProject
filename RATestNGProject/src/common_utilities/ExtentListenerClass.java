package common_utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentListenerClass implements ITestListener {
	ExtentSparkReporter sparkReporter;
	ExtentReports extentReport;
	ExtentTest test;
	
	public void reportConfigurations() {
		sparkReporter = new ExtentSparkReporter(".\\ExtentReport\\report.html");
		extentReport=new ExtentReports();
		extentReport.attachReporter(sparkReporter);
		
		extentReport.setSystemInfo("OS" , "Windows 11");
		extentReport.setSystemInfo("user", "lenovo");
		
		sparkReporter.config().setDocumentTitle("Rest-Assured Extent Report");
		sparkReporter.config().setReportName("First Extent Report");
		sparkReporter.config().setTheme(Theme.DARK);
	}
	public void onStart(ITestContext result) {
		reportConfigurations();
		System.out.println("On start method invoked...");
	}
	public void onFinish(ITestContext result) {
		System.out.println("On Finish Method invoked...");
		extentReport.flush();
	}
	public void onTestFailure(ITestResult result) {
		System.out.println("Name of the Test Failed : "+result.getName());
		test = extentReport.createTest(result.getName());
		test.log(Status.FAIL, MarkupHelper.createLabel("Name of the Failed Test Case : "+result.getName(), ExtentColor.RED));
	}
	public void onTestSkipped(ITestResult result) {
		System.out.println("Name of the Test Skipped : "+result.getName());
		test = extentReport.createTest(result.getName());
		test.log(Status.SKIP, MarkupHelper.createLabel("Name of the Failed Test Case : "+result.getName(), ExtentColor.YELLOW));
	}
	public void onTestStart(ITestResult result) {
		System.out.println("Name of the method Started : "+result.getName());
	}
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("Name of the method Passed : "+result.getName());
		test = extentReport.createTest(result.getName());
		test.log(Status.PASS, MarkupHelper.createLabel("Name of the Failed Test Case : "+result.getName(), ExtentColor.GREEN));
	}
	public void onTestFailureButWithinSuccessPercentage(ITestResult result) {
		
	}
}
