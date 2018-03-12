package com.framework.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class OptionListUtil {
	  /**
	   * Selects the option of the given element with the matching text.
	   *
	   * @param optionText - the text the desired option should have
	   * @param listElement - the element with option sub-elements
	   * @throws NoSuchElementException - if there is no option with the desired text
	   */
	  private static Logger logger = Logger.getLogger("selenium");
	  public static void selectOptionOfElement(String optionText, WebElement listElement) throws NoSuchElementException {
	    boolean found = false;
	    List<WebElement> options = listElement.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	      if (option.getText().trim().equalsIgnoreCase(optionText)) {
	        option.click();
	        found = true;
	        break;
	      }
	    }
	
	    if(!found) {
	      throw new NoSuchElementException();
	    }
	  }
	  
	  /**
	   * Selects the option of the given element with the matching option value attribute.
	   *
	   * @param optionValue - the value attribute the desired option should have
	   * @param listElement - the element with option sub-elements
	   * @throws NoSuchElementException - if there is no option with the desired text
	   */
	  public static void selectOptionOfElementByValue(String optionValue, WebElement listElement) throws NoSuchElementException {
	    boolean found = false;
	    List<WebElement> options = listElement.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	      if (option.getAttribute("value").trim().equalsIgnoreCase(optionValue)) {
	        option.click();
	        found = true;
	        break;
	      }
	    }
	
	    if(!found) {
	      throw new NoSuchElementException();
	    }
	  }
	
	  public static String getSelectedOption(WebElement listElement) {
	    List<WebElement> options = listElement.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	      if (option.getAttribute("selected").equalsIgnoreCase("selected")) {
	        return option.getText();
	      }
	    }
	    return null;
	  }
	
	  /**
	   * Selects the option of the given element with either the value attribute or the option text
	   * matching the passed in value. If optionValue is null or empty the first option in the
	   * list will be chosen.
	   *
	   * @param optionText - the text or value the desired option should have
	   * @param listElement - the element with option sub-elements
	   * @throws NoSuchElementException - if there is no option with the desired text
	   */
	  public static void selectOptionOfElementByTextOrValue(String optionValue, WebElement listElement)
	    throws NoSuchElementException {
	    boolean found = false;
	    List<WebElement> options = listElement.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	      if (StringUtils.isEmpty(optionValue)) {
	        found = true;
	        option.click();
	        break;
	      } else {
	        if (option.getAttribute("value").equalsIgnoreCase(optionValue) ||
	            option.getText().equalsIgnoreCase(optionValue)) {
	          option.click();
	          found = true;
	          break;
	        }
	      }
	    }
	
	    if(!found) {
	      throw new NoSuchElementException();
	    }
	  }
	
	  public static String getSelectedOptionValue(WebElement listElement) {
	    List<WebElement> options = listElement.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	      if (option.isSelected()) {
	        return option.getAttribute("value");
	      }
	    }
	    return null;
	  }
	  
	  /**
	   * Gets the selected Option text of the element 
	   * @param listElement - the element with option sub-elements
	   * @return
	   */
	  public static String getSelectedOptionText(WebElement listElement) {
	      List<WebElement> options = listElement.findElements(By.tagName("option"));
	      for (WebElement option : options) {
	        if (option.isSelected()) {
	          return option.getText();
	        }
	      }
	      return null;
	    }
	/**
	   * @description: Gets the All Options text of the element 
	   * @return List of String
	   */
	  public static List<String> getAllOptionsText(WebElement element) {
	    List<String> elementOptionTextList = new ArrayList<String>();
	    List<WebElement> webElementslist = element.findElements(By.tagName("select"));
	    for (WebElement webElement : webElementslist) {
	      List<WebElement> webElementList = webElement.findElements(By.tagName("option"));
	      for (WebElement optionWebElement : webElementList) {
	        elementOptionTextList.add(optionWebElement.getText());
	      }
	    }
	    return elementOptionTextList;
	  }
	
	/**
	   * Selects the option of the given element with the matching option title attribute.
	   *
	   * @param optionValue - the title attribute the desired option should have
	   * @param listElement - the element with option sub-elements
	   * @throws NoSuchElementException - if there is no option with the desired text
	   */
	  public static void selectOptionOfElementByTitle(String optionValue, WebElement listElement) throws NoSuchElementException {
	    boolean found = false;
	    List<WebElement> options = listElement.findElements(By.tagName("option"));
	    for (WebElement option : options) {
	      if (option.getAttribute("title").trim().equalsIgnoreCase(optionValue)) {
	        option.click();
	        found = true;
	        break;
	      }
	    }
	
	    if(!found) {
	      throw new NoSuchElementException();
	    }
	  }
	  
	  /**
	   * @description: Gets max index of an option list
	   * @param listElement
	   * @return max index
	   */
	  public static int getMaxIndexOfOptionList(WebElement listElement) {
	    int listSize = listElement.findElements(By.tagName("option")).size();
	    return listSize - 1;
	  }
	  
	  /**
	   * @author khaderkhan
	   * Selects the desired item from the dropdown(ul-> li[1...9] )
	   * @param dropDown - webElement for dropdown
	   * @param itemName - desired item to be selected from the list
	   * @throws Exception
	   */
	  public static void selectItemByNameInList(WebElement dropDown, final String itemName) throws Exception {     
	         dropDown.click();
	         
	         List<WebElement> listOfLiTags = dropDown.findElement(By.tagName("ul")).findElements(By.tagName("li"));
	  
	         for(WebElement li : listOfLiTags) {
	            String text = li.getText().toString();
	            logger.info("Items in drop down ="+text);
	            if(text.equals(itemName)) {
	                //do whatever you want and don't forget break
	                // click on the selected item
	                logger.info("Item selected in drop down is ="+text);
	                li.click();
	                break;
	            }
	         }
	  }
	  
	  /**
	   * @author khaderkhan
	   * Selects the desired item from the dropdown(ul-> li[1...9] )
	   * @param dropDown - webElement for dropdown
	   * @param itemName - desired item to be selected from the list
	   * @throws Exception
	   */
	  public static void SelectCellDropDownByName(WebElement dropDown, final String itemName) throws Exception {
	      
	         //dropDown.click();
	         
	         List<WebElement> listOfLiTags = dropDown.findElements(By.tagName("li"));
	  
	         for(WebElement li : listOfLiTags) {
	            String text = li.getText().toString();
	            logger.info("Cell Items in drop down ="+text);
	            if(text.equals(itemName)) {
	                //do whatever you want and don't forget break
	                // click on the selected item
	                logger.info("Cell Item selected in drop down is ="+text);
	                li.click();
	                break;
	            }
	         }
	  }
	  
	  public static void selectItemByName(WebElement webElement, final String itemName) {
	    try {
	      
	        Boolean found = false;
	        List<WebElement> listOfOptionTags = webElement.findElements(By.tagName("option"));
	        
	        for(WebElement option : listOfOptionTags) {
	           String text = option.getText().toString();
	           logger.info("Items in drop down ="+text);
	           if(text.equals(itemName)) {
	               logger.info("Item selected in drop down is ="+text);
	               option.click();
	               found =true;
	               break;
	           }
	        }
	        if (!found)
	           webElement.findElement(By.xpath("option[" + 1 + "]")).click();
	      
	      
	        /*webElement.click();
	        if (itemName.isEmpty())
	        {
	          webElement.click();
	          return;
	        }
	        webElement.findElement(By.xpath("option[.='" + itemName + "']")).click();*/
	    }
	    catch(NoSuchElementException e)
	    {
	        logger.info("choose the default option...");
	        webElement.findElement(By.xpath("option[" + 1 + "]")).click();
	    }
	}
	
	public static void selectItemByIndex(WebElement webElement, final int index) {
	  
	    try {
	        webElement.click();
	        webElement.findElement(By.xpath("option[" + index + "]")).click();
	    }
	    catch(NoSuchElementException e)
	    {
	        webElement.findElement(By.xpath("option[" + 1 + "]")).click();
	    }
	    
	}
	
	/**
	* @description : Selects the drop down of the element based on the index.
	* @param selectElement
	* @param index
	*/
	public static void selectDropDown(WebElement selectElement, int index) {
	      selectElement.sendKeys("");
	      selectElement.click();
	      Select select = new Select(selectElement);
	      select.selectByIndex(index);
	}
	
	
	public static void selectDropDownByValue(WebElement selectElement, String value) {
	      Select select = new Select(selectElement);
	      select.selectByVisibleText(value);
	}
  
}
