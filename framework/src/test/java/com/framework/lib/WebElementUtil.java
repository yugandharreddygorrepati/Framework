package com.framework.lib;
import static com.framework.lib.Driver.delay;
import static com.framework.lib.Driver.driver;
import static com.framework.lib.Driver.waitForElementToDisplay;
import static com.framework.lib.Driver.waitForElementToEnable;
import static com.framework.lib.InitTests.BROWSER_TYPE;
import static com.framework.lib.MyExtentReports.test;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import com.relevantcodes.extentreports.LogStatus;
/**
 * @description: Utilities for Web element actions
 */
public class WebElementUtil
{
	private static final Logger logger = Logger.getLogger(WebElementUtil.class);
	/**
	 * @throws InterruptedException
	 * @description:Sets the input text after the element is displayed
	 */
	public static void setInput(WebElement element, String value)
	{
		if (BROWSER_TYPE.equalsIgnoreCase("ie"))
		{
			System.out.println("came in ie block input");
			waitForElementToDisplay(element);
			waitForElementToEnable(element);
			element.click(); // This below line
			new Actions(driver).moveToElement(element).perform();
			element.clear();
			WebElementUtil.clickElementUsingJavaScript(element);
			if (value != null)
			{
				element.sendKeys(value);
			}
		} else
		{
			try
			{
				Thread.sleep(1000);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			element.clear();
			WebElementUtil.clickElementUsingJavaScript(element);
			if (value != null)
			{
				element.sendKeys(value);
			}
		}
	}
	/**
	 * @description:Clicks on a web element after element is displayed
	 */
	public static void clickElement(WebElement element)
	{
		waitForElementToDisplay(element);
		waitForElementToEnable(element);
		element.click();
	}
	/**
	 * @description:Double clicks on an element
	 */
	public static void doubleClickElement(WebElement element)
	{
		Actions action = new Actions(driver);
		action.moveToElement(element).doubleClick(element).build().perform();
	}
	/**
	 * @description:Clicks on a web element using Java Script Executor after
	 *                     element is displayed
	 */
	public static void clickElementUsingJavaScript(WebElement element)
	{
		String attribute = element.getAttribute("outerHTML");
		waitForElementToEnable(element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		test.log(LogStatus.INFO, "clickElementUsingJavaScript() ", "clicked on " + attribute);
	}
	/**
	 * @description: Poll until text display
	 * @param element
	 */
	public static void pollUntilTextDisplay(WebElement element)
	{
		for (int elementDispalyCount = 0; elementDispalyCount < 100; elementDispalyCount++)
		{
			try
			{
				if (element.getText() != null)
				{
					break;
				}
			} catch (Exception e)
			{
				refreshpage();
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException interuptException)
				{
				}
			}
		}
	}
	/**
	 * @description: refresh the checkout page with robo script
	 */
	public static void refreshpage()
	{
		try
		{
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_F5);
			robot.keyRelease(KeyEvent.VK_F5);
		} catch (AWTException e)
		{
			logger.info("AWTException:Page will not be refereshed");
		}
		// Need time to reload the page
		try
		{
			Thread.sleep(3000);
		} catch (InterruptedException e)
		{
			logger.info("InterruptedException:Wait will not applied");
		}
	}
	/**
	 * @description: refresh the page with Webdriver
	 */
	public static void refreshPageWithWebdriver()
	{
		driver.navigate().refresh();
	}
	/**
	 * @description : Gets the rgb code of a web element and gets the hex code
	 *              from rgb code.
	 * @param Webelement
	 * @return Hex color code for the web element
	 */
	public static String getRgbColorInHexCode(WebElement webElement)
	{
		String[] webElementRgb = webElement.getCssValue("color").replaceAll("(rgba)|(rgb)|(\\()|(\\s)|(\\))", "")
				.split(",");
		String hexColorCode = String.format("#%s%s%s", getHexValuefromCss(Integer.parseInt(webElementRgb[0])),
				getHexValuefromCss(Integer.parseInt(webElementRgb[1])),
				getHexValuefromCss(Integer.parseInt(webElementRgb[2])));
		return hexColorCode;
	}
	/**
	 * @description : Gets the hex value from css.
	 * @param :
	 *            Rg color code
	 * @return : Hex code
	 */
	public static String getHexValuefromCss(int rgb)
	{
		StringBuilder hexcodeBuilder = new StringBuilder(Integer.toHexString(rgb & 0xff));
		while (hexcodeBuilder.length() < 2)
		{
			hexcodeBuilder.append("0");
		}
		return hexcodeBuilder.toString().toUpperCase();
	}
	/**
	 * @description : Gets the value of "font-family" attribute of a web element
	 *              .
	 * @param webElement
	 * @return : font styles of the web element.
	 */
	public static String getFontStyle(WebElement webElement)
	{
		return webElement.getCssValue("font-family");
	}
	/**
	 * @description : Gets the page source of the current page.
	 * @return : page source as string.
	 */
	public static String getPageSource()
	{
		return driver.getPageSource();
	}
	/**
	 * @description : Gets the value of "font-size" attribute of a web element .
	 * @param webElement
	 * @return : font size of the web element.
	 */
	public static String getFontSize(WebElement webElement)
	{
		return webElement.getCssValue("font-size");
	}
	/**
	 * @description : Gets the value of "font-weight" attribute of a web element
	 *              .
	 * @param webElement
	 * @return : String,font weight of the web element.
	 */
	public static String getFontWeight(WebElement webElement)
	{
		return webElement.getCssValue("font-weight");
	}
	/**
	 * @description:Hovers over parent element and clicks on child element
	 */
	public static void hoverAndClickOnElement(WebElement parentElement, WebElement childElement)
	{
		Actions action = new Actions(driver);
		action.moveToElement(parentElement).build().perform();
		delay(2000);
		childElement.click();
	}
	/**
	 * @description:Hovers over parent element
	 */
	public static void hoverOverElement(WebElement element)
	{
		Actions action = new Actions(driver);
		action = action.moveToElement(element);
		action.build().perform();
		delay(2000);
	}
	/**
	 * @description:Fetches link of a web element using Java Script Executor
	 *                      after element is displayed
	 */
	public static String getHrefFromWebElementUsingJavaScript(WebElement element)
	{
		waitForElementToDisplay(element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript("return arguments[0].getAttribute(\"href\")", element).toString();
	}
	/**
	 * @description:Hovers over parent element and moves the mouse by a certain
	 *                     offset from the element
	 */
	public static void hoverOverElementWithOffset(WebElement element)
	{
		Actions action = new Actions(driver);
		action = action.moveToElement(element).moveByOffset(110, 78);// random
																		// value
																		// of x
																		// and y
																		// coordinate
																		// specified
		action.build().perform();
		Driver.delay(10000);
	}
	/**
	 * @description:Fetches link of a web element using Java Script Executor
	 *                      after element is displayed
	 */
	public static String getTextFromWebElementUsingJavaScript(WebElement element)
	{
		waitForElementToDisplay(element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript("return arguments[0].text", element).toString();
	}
	/**
	 * @description : Verifies if the checkbox is already selected.
	 * @param driver
	 * @param checkboxpath
	 * @return
	 */
	public static boolean verifyCheckBox(WebElement checkBox)
	{
		boolean isChecked = false;
		try
		{
			isChecked = checkBox.isSelected();
		} catch (Exception e)
		{
			logger.info(e.getMessage());
		}
		return isChecked;
	}
	/**
	 * @description:Fetches link of a web element using Java Script Executor
	 *                      after element is displayed
	 */
	public static String getIdFromWebElementUsingJavaScript(WebElement element)
	{
		waitForElementToDisplay(element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		return executor.executeScript("return arguments[0].id", element).toString();
	}
	/**
	 * @author khaderkhan
	 * @description: Equivalent to sendkeys in special cases
	 */
	public static void setValueUsingJavaScript(WebElement element, String value)
	{
		waitForElementToDisplay(element);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(
				"document.getElementById('" + element.getAttribute("id") + "').innerHTML = '" + value + "'", element);
	}
	/**
	 * @description : Clears the web element input and sets the input value with
	 *              the provided parameter.
	 * @param input
	 * @param element
	 */
	/*
	 * public static void setInput(WebElement element, String input) { String
	 * del = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE; //
	 * element.sendKeys(""); // if (element.isDisplayed()) { // element.clear();
	 * element.sendKeys(del + input); // } }
	 */
	public static WebElement getElementWithLinkText(String locatorText)
	{
		By loc = By.partialLinkText(locatorText);
		waitForElementToDisplay(loc);
		return driver.findElement(loc);
	}
	public static WebElement getElementWithPartialLinkText(String locatorText)
	{
		By loc = By.partialLinkText(locatorText);
		waitForElementToDisplay(loc);
		return driver.findElement(loc);
	}
	public static WebElement getElementWithTitle(String tagName, String tagHasTitle)
	{
		return driver.findElement(By.cssSelector(tagName + "[title='" + tagHasTitle + "']"));
	}
	public static void selEleByVisbleText(WebElement selectDropDown, String visbleText)
	{
		Select sel = new Select(selectDropDown);
		sel.selectByVisibleText(visbleText);
		// return driver.findElement(By.cssSelector(tagName + "[title='" +
		// tagHasTitle + "']"));
	}
	/**
	 * @param table
	 */
	public static List<String> getTableData(WebElement tableLoc)
	{
		List<String> myTabdata = new ArrayList<String>();
		waitForElementToDisplay(tableLoc);
		List<WebElement> trs = tableLoc.findElements(By.cssSelector("tr:not([class*='ng-hide'])"));
		for (WebElement tr : trs)
		{
			List<WebElement> tds = tr.findElements(By.tagName("td"));
			for (WebElement td : tds)
			{
				myTabdata.add(td.getText());
			}
		}
		return myTabdata;
	}
	/**
	 * @param table,row
	 *            and col index
	 */
	public static WebElement getTableColumn(WebElement rowLoc, int colIndex)
	{
		waitForElementToDisplay(rowLoc);
		List<WebElement> tds = rowLoc.findElements(By.tagName("td"));
		return tds.get(colIndex);
	}
	/**
	 * @param table
	 */
	public static int getNoOfRowsInTable(WebElement tableLoc)
	{
		waitForElementToDisplay(tableLoc);
		List<WebElement> trs = tableLoc.findElements(By.cssSelector("tbody>tr:not([class*='ng-hide'])"));
		System.out.println("rows" + trs.size());
		return trs.size();
	}
	/**
	 * @param table
	 */
	public static int getNoOfColInRow(WebElement rowLoc)
	{
		waitForElementToDisplay(rowLoc);
		List<WebElement> tds = rowLoc.findElements(By.tagName("td"));
		return tds.size();
	}
	/**
	 * @param table
	 */
	public static int getSizeOfTable(WebElement tableLoc)
	{
		int c = 0;
		waitForElementToDisplay(tableLoc);
		List<WebElement> trs = tableLoc.findElements(By.cssSelector("tbody>tr:not([class*='ng-hide'])"));
		for (WebElement tr : trs)
		{
			List<WebElement> tds = tr.findElements(By.tagName("td"));
			for (WebElement td : tds)
			{
				c++;
			}
		}
		return c;
	}
	/**
	 * @param table,row
	 *            and col index
	 */
	public static WebElement getTableColumnByIndex(WebElement tableLoc, int rowIndex, int colIndex)
	{
		waitForElementToDisplay(tableLoc);
		List<WebElement> trs = tableLoc.findElements(By.cssSelector("tbody>tr:not([class*='ng-hide']"));
		WebElement row = trs.get(rowIndex);
		List<WebElement> tds = row.findElements(By.tagName("td"));
		return tds.get(colIndex);
	}
	/**
	 * Scrolls to web element specified
	 *
	 * @param driver
	 * @param element
	 * @throws InterruptedException
	 */
	public static void scrollToElement(final WebElement element) throws InterruptedException
	{
		Thread.sleep(4000); // we cannot use explicit wait here, the element
							// is
							// only visible after scrolling
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	/**
	 * @param table
	 */
	public static int getWebElementCount(List<WebElement> element)
	{
		// waitForElementToDisplay(element);
		// List<WebElement> tds = element.f);
		return element.size();
		// return tds.size();
	}
	public static List<String> getAllValuesFromDrop(Select drop)
	{
		List<String> values = new ArrayList<String>();
		for (WebElement el : drop.getOptions())
		{
			values.add(el.getText());
		}
		return values;
	}
	public static void sleep(int secs)
	{
		try
		{
			Thread.sleep(secs * 1000);
		} catch (Exception e)
		{
			e.getMessage();
		}
	}
}