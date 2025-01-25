package tests;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pages.AccountSuccessPage;
import pages.HeaderOptions;
import pages.MyAccountPage;
import pages.NewsletterSubscriptionPage;
import pages.RegisterAccountPage;
import pages.RightColumnOptions;
import utils.CommonUtils;

public class Register {
	
	WebDriver driver;
	Properties prop;
	HeaderOptions headerOptions;
	RegisterAccountPage registerAccountPage;
	AccountSuccessPage accountSuccessPage;
	RightColumnOptions rightColumnOptions;
	MyAccountPage myAccountPage;
	NewsletterSubscriptionPage newsletterSubscriptionPage;
	
	@BeforeMethod
	public void setup() throws IOException {
	
		prop = new Properties();
		FileReader fr = new FileReader(System.getProperty("user.dir")+"\\src\\test\\resources\\ProjectData.properties");
		prop.load(fr);
		
		String browser = prop.getProperty("browserName");
		
		if(browser.equals("chrome")) {
			driver = new ChromeDriver();
		}else if(browser.equals("firefox")) {
			driver = new FirefoxDriver();
		}else if(browser.equals("edge")) {
			driver = new EdgeDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get(prop.getProperty("applicationURL"));
		
		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccount();
		registerAccountPage = headerOptions.selectRegisterOption();
	
	}
	
	@AfterMethod
	public void teardown() {
		
		driver.quit();
		
	}
	
	@Test(priority=1)
	public void verifyRegisteringUsingMandatoryFields() {
	
		registerAccountPage.enterFirstName(prop.getProperty("firstName"));
		registerAccountPage.enterLastName(prop.getProperty("lastName"));
		registerAccountPage.enterEmail(CommonUtils.generateNewEmail());
		registerAccountPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerAccountPage.enterPassword(prop.getProperty("validPassword"));
		registerAccountPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerAccountPage.selectPrivacyPolicyField();
		accountSuccessPage = registerAccountPage.clickOnContinueButton();
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		Assert.assertTrue(rightColumnOptions.isUserLoggedIn());
		accountSuccessPage = rightColumnOptions.getAccountSuccessPage();
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
	
	}
	
	@Test(priority=2)
	public void verifyRegisteringAccountUsingAllFields() {
		
		registerAccountPage.enterFirstName(prop.getProperty("firstName"));
		registerAccountPage.enterLastName(prop.getProperty("lastName"));
		registerAccountPage.enterEmail(CommonUtils.generateNewEmail());
		registerAccountPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerAccountPage.enterPassword(prop.getProperty("validPassword"));
		registerAccountPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerAccountPage.selectYesNewsletterOption();
		registerAccountPage.selectPrivacyPolicyField();
		accountSuccessPage = registerAccountPage.clickOnContinueButton();
		rightColumnOptions = accountSuccessPage.getRightColumnOptions();
		Assert.assertTrue(rightColumnOptions.isUserLoggedIn());
		accountSuccessPage = rightColumnOptions.getAccountSuccessPage();
		Assert.assertTrue(accountSuccessPage.didWeNavigateToAccountSuccessPage());
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
		
	}

	
	@Test(priority=3)
	public void verifyRegisteringAccountBySelectingYesNewsletterOption() {
		
		registerAccountPage.enterFirstName(prop.getProperty("firstName"));
		registerAccountPage.enterLastName(prop.getProperty("lastName"));
		registerAccountPage.enterEmail(CommonUtils.generateNewEmail());
		registerAccountPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerAccountPage.enterPassword(prop.getProperty("validPassword"));
		registerAccountPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerAccountPage.selectYesNewsletterOption();
		registerAccountPage.selectPrivacyPolicyField();
		accountSuccessPage = registerAccountPage.clickOnContinueButton();
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		newsletterSubscriptionPage = myAccountPage.clickOnSubscribeOrUnsubscribeNewsletterOption();
		Assert.assertTrue(newsletterSubscriptionPage.isYesNewsletterOptionSelected());
	
	}
	
	@Test(priority=4)
	public void verifyRegisteringAccountBySelectingNoNewsletterOption() {
		
		registerAccountPage.enterFirstName(prop.getProperty("firstName"));
		registerAccountPage.enterLastName(prop.getProperty("lastName"));
		registerAccountPage.enterEmail(CommonUtils.generateNewEmail());
		registerAccountPage.enterTelephone(prop.getProperty("telephoneNumber"));
		registerAccountPage.enterPassword(prop.getProperty("validPassword"));
		registerAccountPage.enterConfirmationPassword(prop.getProperty("validPassword"));
		registerAccountPage.selectNoNewsletterOption();
		registerAccountPage.selectPrivacyPolicyField();
		accountSuccessPage = registerAccountPage.clickOnContinueButton();
		myAccountPage = accountSuccessPage.clickOnContinueButton();
		newsletterSubscriptionPage = myAccountPage.clickOnSubscribeOrUnsubscribeNewsletterOption();
		Assert.assertTrue(newsletterSubscriptionPage.isNoNewsletterOptionSelected());
	
	}
	
	@Test(priority = 5)
	public void verifyPrivacyPolicySelectionStatusInRegisterAccountPage() {
		
		Assert.assertFalse(registerAccountPage.isPrivacyPolicyFieldSelected());
	
	}
	
}
