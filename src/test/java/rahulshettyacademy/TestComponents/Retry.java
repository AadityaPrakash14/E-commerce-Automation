package rahulshettyacademy.TestComponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {
	int count = 0;
	int maxRetry = 1;

	@Override
	public boolean retry(ITestResult result) {

		if (maxRetry > count) {
			count++;
			return true;
		}
		return false;
	}
}
