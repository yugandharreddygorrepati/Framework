package com.framework.lib;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


/**
 * @description: Utilities for Web page 
 */
public class WebPageUtil {
  
  /**
   * @description: Extracts the page source data
   * @return page source data
   */
  public static String getPageSource(WebDriver driver) {
    String pageSource = driver.getPageSource();
    return pageSource;
  }

    /**
    * @description: Extracts the page source data with a starting keyword and ending keyword
    * @param startKeyword
    * @param endKeyword
    * @return page source data with starting keyword and ending keyword
    */
   public static String getPageSourceData(WebDriver driver,String startKeyword, String endKeyword) {
     String pageSource = driver.getPageSource();
     String pageSourceData = pageSource.substring(pageSource.indexOf(startKeyword), pageSource.indexOf(endKeyword)
       + endKeyword.length());
     return pageSourceData;
   }

   /**
    * @description: Get the page title
    * @return
    */
   public static String getPageTitle(WebDriver driver) {
     return driver.getTitle();
   }
  
   /**
    * @description : Gets the variables starting with "s.*" in the java script.
    * @param propID
    * @return variable value
    */
   public static String getPropertyValueUsingJavaScriptExecutor(WebDriver driver,String propID) {
     JavascriptExecutor js = (JavascriptExecutor) driver;
     String prop = (String) js.executeScript("return s." + propID + ";");
     return prop;
   }

   /**
    * @description: refresh the page with Webdriver
    */
  public static void refreshPageWithWebdriver(WebDriver driver){
	  driver.navigate().refresh();
  }  
  
  public static void clickBackButton(WebDriver driver){
	  driver.navigate().back();
  }
  
  /**
   * @description : Gets the variables starting with "s.*" in the java script.
   * @param propID
   * @return variable value
   */
  public static boolean getAttributeCheckedValueUsingJavaScriptExecutor(WebDriver driver,String className, String position) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    //String prop = (String) js.executeScript("return " + propID + ";");
   //  String prop = (String) js.executeScript(" return $(\"#"+propID+"\").attr('checked')"+";");
   // $("#om_apm_wt35:wtMainContent:wtDuplicateCheck_Enableduplicatecheckfornew").attr('checked')
  //  String prop = (String) js.executeScript(" return $(\"#om_apm_wt35:wtMainContent:wtDuplicateCheck_Enableduplicatecheckfornew\").attr('checked')"+";");
    
  // return (Boolean) js.executeScript(" return $(\""+className+"\").["+position+"].checked"+";");
    return (Boolean) js.executeScript(" return $(\""+className+"\")["+position+"].checked ;");
    //return value;
  }
   
  
  
  
}
