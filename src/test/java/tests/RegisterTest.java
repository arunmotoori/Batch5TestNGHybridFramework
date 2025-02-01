package tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;
import utils.CommonUtils;

public class RegisterTest extends Base {

	public WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplicationURL();
		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccount();
		registerAccountPage = headerOptions.selectRegisterOption();

	}

	@AfterMethod
	public void teardown() {
		quitBrowser(driver);
	}

	@Test(priority = 1)
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

	@Test(priority = 2)
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

	@Test(priority = 3)
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

	@Test(priority = 4)
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
