package com.framework.lib;
import static com.framework.lib.MyExtentReports.test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import com.relevantcodes.extentreports.LogStatus;
/**
 * @author YugandharReddyGorrep
 *
 */
public class EventListner implements WebDriverEventListener
{
	@Override
	public void beforeNavigateTo(String url, WebDriver driver)
	{
	}
	/**
	 * @description:Logs each navigation of AUT into extent report.
	 * 
	 * @param url
	 * @param WebDriver
	 * 
	 */
	@Override
	public void afterNavigateTo(String url, WebDriver arg)
	{
		test.log(LogStatus.INFO, "afterNavigateTo()", "navigated to " + url + " page title : " + arg.getTitle());
		System.out.println("navigated to " + url + " page title : " + arg.getTitle());
	}
	@Override
	public void beforeNavigateBack(WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void afterNavigateBack(WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void beforeNavigateForward(WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void afterNavigateForward(WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void beforeNavigateRefresh(WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void afterNavigateRefresh(WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void beforeFindBy(By by, WebElement element, WebDriver driver)
	{
		// test.log(LogStatus.INFO, "beforeFindBy() ", by.toString());
	}
	@Override
	public void afterFindBy(By by, WebElement element, WebDriver driver)
	{
		// test.log(LogStatus.INFO, "element found with ", by.toString());
	}
	@Override
	public void beforeClickOn(WebElement element, WebDriver driver)
	{
		// test.log(LogStatus.INFO, "beforeClickOn()",
		// "before click on locator - [" + element.toString().split("-> ",
		// 2)[1]);
	}
	/**
	 * @description:Logs each click of AUT into extent report.
	 * 
	 * @param WebElement
	 * @param WebDriver
	 * 
	 */
	@Override
	public void afterClickOn(WebElement element, WebDriver driver)
	{
		try
		{
			test.log(LogStatus.INFO, "afterClickOn() ", "clicked on " + element.getAttribute("outerHTML"));
			System.out.println("afterClickOn " + element);
		} catch (StaleElementReferenceException e)
		{
			test.log(LogStatus.INFO, "afterClickOn()", "ignoring staleElement Exception " + e.getMessage());
			System.out.println("afterClickOn " + element);
		}
	}
	@Override
	public void beforeScript(String script, WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void afterScript(String script, WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	@Override
	public void onException(Throwable throwable, WebDriver driver)
	{
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.openqa.selenium.support.events.WebDriverEventListener#
	 * beforeChangeValueOf(org.openqa.selenium.WebElement,
	 * org.openqa.selenium.WebDriver)
	 */
	@Override
	public void beforeChangeValueOf(WebElement element, WebDriver driver)
	{
		// TODO Auto-generated method stub
	}
	/**
	 * @description:Logs each change value of AUT into extent report.
	 * 
	 * @param WebElement
	 * @param WebDriver
	 * 
	 */
	@Override
	public void afterChangeValueOf(WebElement element, WebDriver driver)
	{
		try
		{
		test.log(LogStatus.INFO, "afterChangeValueOf()", "after send keys locator - ["
				+ element.toString().split("-> ", 2)[1] + " value - " + element.getAttribute("value"));
		System.out.println("after send keys locator - [" + element.toString().split("-> ", 2)[1] + " value - "
				+ element.getAttribute("value"));
		}
		catch(StaleElementReferenceException e)
		{
			test.log(LogStatus.INFO, "afterChangeValueOf()", "after send keys locator - ["
					+ element.toString().split("-> ", 2)[1] );
			System.out.println("after send keys locator - [" + element.toString().split("-> ", 2)[1] );
		}
	}
}
