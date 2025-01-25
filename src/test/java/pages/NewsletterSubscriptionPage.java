package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewsletterSubscriptionPage {
	
	WebDriver driver;
	
	public NewsletterSubscriptionPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//input[@name='newsletter'][@value='1']")
	private WebElement yesNewsletterOption;
	
	@FindBy(xpath="//input[@name='newsletter'][@value='0']")
	private WebElement noNewsletterOption;
	
	public boolean isYesNewsletterOptionSelected() {
		return yesNewsletterOption.isSelected();
	}
	
	public boolean isNoNewsletterOptionSelected() {
		return noNewsletterOption.isSelected();
	}

}
