package tests;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.Base;
import pages.HeaderOptions;
import utils.CommonUtils;
import utils.MyXLSReader;

public class LoginTest extends Base {

	public WebDriver driver;

	@BeforeMethod
	public void setup() {

		driver = openBrowserAndApplicationURL();
		headerOptions = new HeaderOptions(driver);
		headerOptions.clickOnMyAccount();
		loginPage = headerOptions.selectLoginOption();

	}

	@AfterMethod
	public void teardown() {
		quitBrowser(driver);
	}

	@Test(priority = 1,dataProvider="validCredentialsSupplier")
	public void verifyLoggingIntoApplicationUsingValidCredentials(HashMap<String, String> map) {

		loginPage.enterEmail(map.get("Email"));
		loginPage.enterPassword(map.get("Password"));
		myAccountPage = loginPage.clickOnLoginButton();
		rightColumnOptions = myAccountPage.getRightColumnOptions();
		Assert.assertTrue(rightColumnOptions.isUserLoggedIn());
		myAccountPage = rightColumnOptions.getMyAccountPage();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());

	}
	
	@DataProvider(name="validCredentialsSupplier")
	public Object[][] testDataForLogin() {
		MyXLSReader myXLSReader = new MyXLSReader("\\src\\test\\resources\\TutorialsNinjaData.xlsx");
		Object[][] data = CommonUtils.getTestData(myXLSReader,"loginWithValidCredentials","login");
		return data;
	}

	@Test(priority = 2)
	public void verifyLoggingIntoApplicationUsingInvalidCredentials() {

		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 3)
	public void verifyLoggingIntoApplicationUsingInvalidEmailAndValidPassword() {

		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 4)
	public void verifyLoggingIntoApplicationUsingValidEmailAndValidPassword() {

		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);

	}

	@Test(priority = 5)
	public void verifyLoggingIntoApplicationWithoutEnteringCredentials() {

		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertNotEquals(loginPage.getWarningMessage(), expectedWarning);

	}

}
