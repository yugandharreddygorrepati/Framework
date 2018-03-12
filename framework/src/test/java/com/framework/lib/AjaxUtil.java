package com.framework.lib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;

public class AjaxUtil
{
	/**
	 * Make sure an ajax call has time to finish.
	 *
	 * @return
	 */
	public static Boolean waitForAjaxToComplete(WebDriver Driver)
	{
		// WebDriver driver = GlobalVariables.driver.get();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(Driver).withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
		Boolean finished = wait.until(new Function<WebDriver, Boolean>() {
			// Wait until the ajax has finished
			public Boolean apply(WebDriver driver)
			{
				Object result = ((JavascriptExecutor) driver).executeScript("var b = jQuery.active == 0; return b;");
				return (Boolean) result;
			}
		});
		// Driver.delay(500);
		return finished;
	}
	
	/**
	 * This is a more robust version of waitForAjaxToComplete. Occasionally
	 * jQuery will have an ajax request fail and will fail to decrement
	 * jQuery.active. This takes that into account.
	 *
	 * @param operations
	 *            and {@link AjaxOperations} object containing the actions that
	 *            will result in ajax calls.
	 */
	public static void performAjaxAndWaitForCompletion(AjaxOperations operations, WebDriver Driver)
	{
		// final WebDriver driver = GlobalVariables.driver.get();
		final JavascriptExecutor executor = (JavascriptExecutor) Driver;
		// get the number of active connections - not guaranteed to be 0 if a
		// request has failed.
		final int activeConnections = getActiveConnections(executor);
		operations.execute();
		// Driver.delay(5000);
		Wait<WebDriver> wait = new FluentWait<WebDriver>(Driver).withTimeout(5, TimeUnit.SECONDS)
				.pollingEvery(500, TimeUnit.MILLISECONDS).ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			// Wait until the ajax has finished
			public Boolean apply(WebDriver driver)
			{
				// compare the current number of connections with the number we
				// had before the operation.
				// Since other ajax calls may have been running we just make
				// sure that the current #
				// of connections is less than or equal to what we started with.
				return getActiveConnections(executor) <= activeConnections;
			}
		});
		// Driver.delay(500);
	}
	
	private static int getActiveConnections(JavascriptExecutor executor)
	{
		Object jsResult = executor.executeScript("return jQuery.active;");
		return ((Long) jsResult).intValue();
	}
	
	/**
	 * A class to pass operations that will/may result in ajax calls being made.
	 * Put the operations in an overridden execute method.
	 */
	public static abstract class AjaxOperations
	{
		public abstract void execute();
	}
}
