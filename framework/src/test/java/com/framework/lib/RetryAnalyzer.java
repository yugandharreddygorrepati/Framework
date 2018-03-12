package com.framework.lib;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class RetryAnalyzer implements IRetryAnalyzer
{
	private int count = 0;
	private int maxCount = 1; // set your count to re-run test
	// @Override
	public boolean retry(ITestResult result)
	{
		try
		{
			maxCount = Integer.parseInt(InitTests.getPropValue("Retry_test_count").trim());
			System.out.println("Retrying the failed test case:" + maxCount);
			if (count < maxCount)
			{
				count++;
				return true;
			}
		} catch (NumberFormatException e)
		{
			e.getStackTrace();
		}
		return false;
	}
}