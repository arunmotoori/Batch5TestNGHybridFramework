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

import pages.HeaderOptions;
import pages.LoginPage;
import pages.MyAccountPage;
import pages.RightColumnOptions;
import utils.CommonUtils;

public class Login {
	
	WebDriver driver;
	Properties prop;
	HeaderOptions headerOptions;
	LoginPage loginPage;
	MyAccountPage myAccountPage;
	RightColumnOptions rightColumnOptions;
	
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
		loginPage = headerOptions.selectLoginOption();
	
	}
	
	@AfterMethod
	public void teardown() {
		
		driver.quit();
		
	}
	
	@Test(priority=1)
	public void verifyLoggingIntoApplicationUsingValidCredentials() {
		
		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("validPassword"));
		myAccountPage = loginPage.clickOnLoginButton();
		rightColumnOptions = myAccountPage.getRightColumnOptions();
		Assert.assertTrue(rightColumnOptions.isUserLoggedIn());
		myAccountPage = rightColumnOptions.getMyAccountPage();
		Assert.assertTrue(myAccountPage.didWeNavigateToMyAccountPage());
	
	}
	
	@Test(priority=2)
	public void verifyLoggingIntoApplicationUsingInvalidCredentials() {
		
		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		
	}
	
	@Test(priority=3)
	public void verifyLoggingIntoApplicationUsingInvalidEmailAndValidPassword() {
		
		loginPage.enterEmail(CommonUtils.generateNewEmail());
		loginPage.enterPassword(prop.getProperty("validPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		
	}
	
	@Test(priority=4)
	public void verifyLoggingIntoApplicationUsingValidEmailAndValidPassword() {
		
		loginPage.enterEmail(prop.getProperty("existingEmail"));
		loginPage.enterPassword(prop.getProperty("invalidPassword"));
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		
	}
	
	@Test(priority=5)
	public void verifyLoggingIntoApplicationWithoutEnteringCredentials() {
		
		loginPage.clickOnLoginButton();
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(loginPage.getWarningMessage(), expectedWarning);
		
	}
	
	

}
