package com.qa.opencart.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.microsoft.playwright.Page;
import com.qa.opencart.base.BaseClass;
import com.qa.opencart.config.ConfigManager;
import com.qa.opencart.playwrightfactory.PlaywrightFactory;
import com.qa.opencart.utilities.ScreenCapture;
import org.testng.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ExtentReportListeners implements ISuiteListener, ITestListener {

    private static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/test-output/Test-ExtentReport/";
    private static final String FILE_NAME =
            "TestExecutionReport_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                    .format(new Date()) + ".html";
    private static ExtentReports extent = init();
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
    private static ExtentReports extentReports;


    private static ExtentReports init() {

        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }

        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter(OUTPUT_FOLDER + FILE_NAME);
        reporter.config().setReportName("Open Cart Automation Test Results");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("System", "Windows");
        extentReports.setSystemInfo("Author", "SamTech AutomationLabs");
        extentReports.setSystemInfo("Build#", "1.1");
        extentReports.setSystemInfo("Team", "QA");
        extentReports.setSystemInfo("Customer Name", "NAL");

        //extentReports.setSystemInfo("ENV NAME", System.getProperty("env"));

        return extentReports;
    }

    @Override
    public synchronized void onStart(ISuite iSuite) {
        System.out.println("Test Suite "+ iSuite.getName() +" started");
        String env = System.getProperty("env","dev");
        if (env == null || env.isBlank() || env.isEmpty()) {
            env = "dev";
        }
        extent.setSystemInfo("Environment", env);

    }

    @Override
    public synchronized void onFinish(ISuite iSuite) {
        System.out.println(("Test Suite "+ iSuite.getName() +" ended"));
        extent.flush();
        test.remove();
    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);
        String browser = BaseClass.getBrowserName();

        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        /*
         * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
         * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
         */
        extentTest.assignCategory(className);
        extentTest.assignCategory(browser);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));
        MediaEntityBuilder media = captureScreenshot();
        if (media != null) {
            test.get().pass("Test Passed", media.build());
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        MediaEntityBuilder media = captureScreenshot();
        if (media != null) {
            test.get().fail(result.getThrowable(), media.build());
        } else {
            test.get().fail(result.getThrowable());
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        MediaEntityBuilder media = captureScreenshot();
        if (media != null) {
            test.get().skip(result.getThrowable(), media.build());
        } else {
            test.get().skip(result.getThrowable());
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }
    private MediaEntityBuilder captureScreenshot() {

        Page page = PlaywrightFactory.getPage();
        String base64 = ScreenCapture.capture(page);

        if (base64 != null) {
            return MediaEntityBuilder
                    .createScreenCaptureFromBase64String(base64);
        }

        return null;
    }

}
