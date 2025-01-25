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
import pages.SearchPage;

public class Search {
	
	WebDriver driver;
	Properties prop;
	HeaderOptions headerOptions;
	SearchPage searchPage;
	
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
		
	}
	
	@AfterMethod
	public void teardown() {
		
		driver.quit();
	}
	
	@Test(priority=1)
	public void verifySearchingWithExistingProduct() {
		
		headerOptions.enterSearchProduct(prop.getProperty("existingProduct"));
		searchPage = headerOptions.clickOnSearchButton();
		Assert.assertTrue(searchPage.isProductDisplayedInSearchResults());
		
	}
	
	@Test(priority=2)
	public void verifySearchingWithNonExistingProductName() {
		
		headerOptions.enterSearchProduct(prop.getProperty("nonExistingProduct"));
		searchPage = headerOptions.clickOnSearchButton();
		String expectedMessage = "There is no product that matches the search criteria.";
		Assert.assertEquals(searchPage.getNoProductMessage(), expectedMessage);
	
	}
	
	@Test(priority=3)
	public void verifySearchingWithoutProvidingProductName() {
		
		searchPage = headerOptions.clickOnSearchButton();
		String expectedMessage = "There is no product that matches the search criteria.";
		Assert.assertEquals(searchPage.getNoProductMessage(), expectedMessage);
		
	}

}
