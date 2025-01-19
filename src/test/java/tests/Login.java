package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.CommonUtils;

public class Login {
	
	ChromeDriver driver;
	
	@BeforeMethod
	public void setup() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.get("https://tutorialsninja.com/demo/");
		
		driver.findElement(By.xpath("//span[text()='My Account']")).click();
		driver.findElement(By.linkText("Login")).click();
		
	}
	
	@AfterMethod
	public void teardown() {
		
		driver.quit();
		
	}
	
	@Test(priority=1)
	public void verifyLoggingIntoApplicationUsingValidCredentials() {
		
		driver.findElement(By.id("input-email")).sendKeys("amotooricap1@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='list-group-item'][text()='Logout']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.linkText("Edit your account information")).isDisplayed());
	
	}
	
	@Test(priority=2)
	public void verifyLoggingIntoApplicationUsingInvalidCredentials() {
		
		driver.findElement(By.id("input-email")).sendKeys(CommonUtils.generateNewEmail());
		driver.findElement(By.id("input-password")).sendKeys("67890");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(), expectedWarning);
		
	}
	
	@Test(priority=3)
	public void verifyLoggingIntoApplicationUsingInvalidEmailAndValidPassword() {
		
		driver.findElement(By.id("input-email")).sendKeys(CommonUtils.generateNewEmail());
		driver.findElement(By.id("input-password")).sendKeys("12345");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(), expectedWarning);
		
	}
	
	@Test(priority=4)
	public void verifyLoggingIntoApplicationUsingValidEmailAndValidPassword() {
		
		driver.findElement(By.id("input-email")).sendKeys("amotooricap1@gmail.com");
		driver.findElement(By.id("input-password")).sendKeys("67890");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(), expectedWarning);
		
	}
	
	@Test(priority=5)
	public void verifyLoggingIntoApplicationWithoutEnteringCredentials() {
		
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		String expectedWarning = "Warning: No match for E-Mail Address and/or Password.";
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']")).getText(), expectedWarning);
		
	}
	
	

}
