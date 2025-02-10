package demo;

import demo.wrappers.Wrappers;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;

public class TestCases {
    ChromeDriver driver;
    WebDriverWait wait;
    static String generatedEmail;
    String password = "Test@1234";

    @Test(priority = 1)
    public void testCase01_RegisterUser() throws InterruptedException {
        System.out.println("Start of Testcase01 ");
        driver.get("https://demowebshop.tricentis.com/");


        Wrappers.click(driver.findElement(By.xpath("//a[contains(text(),'Register')]")), driver);
        Wrappers.click(driver.findElement(By.id("gender-male")), driver);
        Wrappers.sendKeys(driver.findElement(By.id("FirstName")), "Sharath", driver);
        Wrappers.sendKeys(driver.findElement(By.id("LastName")), "raikoty", driver);

        generatedEmail = "sharath" + System.currentTimeMillis() + "@test.com";
        Wrappers.sendKeys(driver.findElement(By.id("Email")), generatedEmail, driver);

        Wrappers.sendKeys(driver.findElement(By.id("Password")), password, driver);
        Wrappers.sendKeys(driver.findElement(By.id("ConfirmPassword")), password, driver);
        Wrappers.click(driver.findElement(By.id("register-button")), driver);
       //Thread.sleep(3000);
        String successMessage = driver.findElement(By.xpath("//div[@class='result']")).getText();
        Assert.assertTrue(successMessage.contains("Your registration completed"), "Registration Failed");

        System.out.println("Generated Email: " + generatedEmail);
        Wrappers.click(driver.findElement(By.xpath("//a[contains(text(),'Log out')]")), driver);
        System.out.println("End of Testcase01 ");
    }

    @Test(priority = 2, dependsOnMethods = "testCase01_RegisterUser")
    public void testCase02_LoginWithValidCredentials() {
        System.out.println("Start of Testcase02 ");
        driver.get("https://demowebshop.tricentis.com/login");

        Wrappers.sendKeys(driver.findElement(By.id("Email")), generatedEmail, driver);
        Wrappers.sendKeys(driver.findElement(By.id("Password")), password, driver);
        Wrappers.click(driver.findElement(By.cssSelector("input[value='Log in']")), driver);

        WebElement account = driver.findElement(By.xpath("//a[contains(text(),'Log out')]"));
        Assert.assertTrue(account.isDisplayed(), "Login Failed");
        System.out.println("End of Testcase02 ");
    }

    @Test(priority = 3)
    public void testCase03_LoginWithInvalidCredentials() {
        driver.get("https://demowebshop.tricentis.com/login");

        Wrappers.sendKeys(driver.findElement(By.id("Email")), "invaliduser@test.com", driver);
        Wrappers.sendKeys(driver.findElement(By.id("Password")), "WrongPass123", driver);
        Wrappers.click(driver.findElement(By.cssSelector("input[value='Log in']")), driver);

        String errorMessage = driver.findElement(By.className("message-error")).getText();
        Assert.assertTrue(errorMessage.contains("Login was unsuccessful"), "Error message not displayed for invalid login");
    }

    @Test(priority = 4)
    public void testCase04_SearchExistingProduct() {
        driver.get("https://demowebshop.tricentis.com/login");
        Wrappers.sendKeys(driver.findElement(By.id("Email")), generatedEmail, driver);
        Wrappers.sendKeys(driver.findElement(By.id("Password")), password, driver);
        Wrappers.click(driver.findElement(By.cssSelector("input[value='Log in']")), driver);

        Wrappers.sendKeys(driver.findElement(By.xpath("//input[@id='small-searchterms']")), "Laptop", driver);
        Wrappers.click(driver.findElement(By.cssSelector("input[value='Search']")), driver);
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='product-item']"));
      
        Assert.assertTrue(products.size()>0, "Product Search Failed");
    }
    public void testCase05_SearchWithoutProduct() {
        driver.get("https://demowebshop.tricentis.com/login");
        Wrappers.sendKeys(driver.findElement(By.id("Email")), generatedEmail, driver);
        Wrappers.sendKeys(driver.findElement(By.id("Password")), password, driver);
        Wrappers.click(driver.findElement(By.cssSelector("input[value='Log in']")), driver);

        Wrappers.sendKeys(driver.findElement(By.xpath("//input[@id='small-searchterms']")), "", driver);
        wait.until(ExpectedConditions.alertIsPresent()); // Wait for alert

        Alert alert = driver.switchTo().alert();
        System.out.println("Alert Text: " + alert.getText());

        alert.accept();
      
       
    }
  

    

    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();
    }
}
