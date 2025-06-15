package rahulshettyacademy.TestComponents;

import java.io.IOException;
import java.lang.reflect.Field;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import rahulshettyacademy.reports.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

    /** One shared ExtentReports instance for the whole suite (singleton). */
    private static final ExtentReports extent = ExtentReporterNG.getReportObject();

    /** One ExtentTest per test-method, kept thread-safe with ThreadLocal. */
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    /* ************************  TestNG listener callbacks  ************************ */

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test); // link this ExtentTest to the current thread
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());

        // --- 1️⃣  Grab the WebDriver instance from the test class (via reflection) ---
        WebDriver failedDriver = extractDriver(result);

        // --- 2️⃣  Capture screenshot & attach to report ---
        try {
            String filePath = getScreenshot(result.getMethod().getMethodName(), failedDriver);
            extentTest.get().addScreenCaptureFromPath(filePath,
                                                     result.getMethod().getMethodName());
        } catch (IOException e) {
            extentTest.get().warning("Screenshot capture failed: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();  // write everything to the HTML report exactly once
    }

    /* ******************************  Helpers  *********************************** */

    /**
     * Uses reflection to fetch the WebDriver field named "driver" from the test instance.
     * Supports inheritance (walks up the class hierarchy).
     */
    private WebDriver extractDriver(ITestResult result) {
        Object testInstance = result.getInstance();
        Class<?> clazz = testInstance.getClass();

        while (clazz != null) {
            try {
                Field driverField = clazz.getDeclaredField("driver");
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(testInstance);
            } catch (NoSuchFieldException ignored) {
                clazz = clazz.getSuperclass(); // look in parent class
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                break;
            }
        }
        return null; // driver not found
    }
}
