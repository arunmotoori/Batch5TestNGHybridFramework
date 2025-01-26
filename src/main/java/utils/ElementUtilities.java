package utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementUtilities {
	
	WebDriver driver;

	public ElementUtilities(WebDriver driver) {
		this.driver = driver;
	}
	
	public boolean isElementSelected(WebElement element) {
		boolean b = false;
		if(isElementDisplayed(element)) {
			b = element.isSelected();
		}
		return b;
	}
	
	public String getTextFromElement(WebElement element) {
		String t = "";
		try {
			t = element.getText();
		}catch(NoSuchElementException e) {
			e.printStackTrace();
		}
		return t;
	}
	
	public void enterTextIntoElement(WebElement element,String text) {
		if(isElementDisplayed(element) && isElementInEnabledState(element)) {
			element.clear();
			element.sendKeys(text);
		}
	}

	public void clickOnElement(WebElement element) {
		if(isElementDisplayed(element) && isElementInEnabledState(element)) {
			element.click();
		}
	}

	public boolean isElementDisplayed(WebElement element) {
		
		boolean b = false;

		try {
			b = element.isDisplayed();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

		return b;
	
	}
	
    public boolean isElementInEnabledState(WebElement element) {
		
		boolean b = false;

		if(isElementDisplayed(element)) {
			b = element.isEnabled();
		}

		return b;
	
	}

}
