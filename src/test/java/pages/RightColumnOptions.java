package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RightColumnOptions {
	
	WebDriver driver;
	
	public RightColumnOptions(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy(xpath="//a[@class='list-group-item'][text()='Logout']")
	private WebElement logoutOption;
	
	public boolean isUserLoggedIn() {
		return logoutOption.isDisplayed();
	}
	
	public AccountSuccessPage getAccountSuccessPage() {
		return new AccountSuccessPage(driver);
	}
	
	public MyAccountPage getMyAccountPage() {
		return new MyAccountPage(driver);
	}

}
