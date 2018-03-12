package com.framework.lib;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
/**
 * @author YugandharReddyGorrep
 *
 */
public class TestListener extends TestListenerAdapter
{
	/**
	 * @description: Calling initialization of extent reports.
	 * 
	 * @throws Exception
	 */
	public TestListener() throws IOException
	{
		new MyExtentReports(); // Initialization of -Extent reports
	}
	protected static Logger logger = Logger.getLogger(TestListener.class);
	static File directory;
	private String folderName;
	@Override
	public void onTestStart(ITestResult testResult)
	{
		// TODO Auto-generated method stub
		logger.info("Test '" + testResult.getName() + "' STARTED\n");
	}
	@Override
	public void onFinish(ITestContext context)
	{
		logger.info("All Tests were Finished.........\n\n");
	}
	@Override
	public void onTestSuccess(ITestResult testResult)
	{
		// TODO Auto-generated method stub
		logger.info("Test '" + testResult.getName() + "' PASSED");
		logger.info("test ID:" + testResult.getMethod().getDescription() + "\n\n");
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void onTestFailure(ITestResult testResult)
	{
		logger.info("Test '" + testResult.getName() + "' FAILED");
		logger.info("test ID:" + testResult.getMethod().getDescription() + "\n\n");
		/*
		 * File scrFile; // time for current thread to load the page
		 * Reporter.setCurrentTestResult(testResult); try { Thread.sleep(5000);
		 * } catch (InterruptedException e1) {
		 * LOGGER.info("InterruptedException:Wait is not enough for page load "
		 * ); } if (isGridEnabled()) { WebDriver augmentedDriver = new
		 * Augmenter().augment(Utils.getDriver()); scrFile = ((TakesScreenshot)
		 * augmentedDriver).getScreenshotAs(OutputType.FILE); } else { scrFile =
		 * ((TakesScreenshot)
		 * Utils.getDriver()).getScreenshotAs(OutputType.FILE); } try { //
		 * String folderName = new
		 * SimpleDateFormat("MM-dd-yyyy_HH_mm_ss").format(new Date()); File
		 * destFile = new File(System.getProperty("user.dir") + File.separator +
		 * "FailedScreenshot" + File.separator + folderName + File.separator +
		 * testResult.getMethod().getMethodName() + ".png");
		 * FileUtils.copyFile(scrFile, destFile); } catch (IOException e) {
		 * LOGGER.info("IOException:Not able to save the screen shot"); }
		 * Reporter.setCurrentTestResult(null);
		 * Utils.getDriver().manage().deleteAllCookies() ;
		 */
	}
	@Override
	public void onTestSkipped(ITestResult arg0)
	{
		// TODO Auto-generated method stub
		// Utils.getDriver().manage().deleteAllCookies() ;
	}
	/*
	 * Deleting the Folder and Files inside the Folder
	 */
	public static boolean deleteDir(File dir)
	{
		if (dir.exists())
		{
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success)
				{
					return false;
				}
			}
		}
		return dir.delete();
	}
	private boolean isGridEnabled()
	{
		String grid = System.getProperty("grid");
		return grid != null && !"false".equalsIgnoreCase(grid);
	}
}
