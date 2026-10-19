
package ecom.Bitm.NinzaHRM.baseFile;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import ecom.Bitm.NinzaHRM.genericUtility.PropertyFileUtility;

public class BaseClass01 {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @FindBy(xpath = "//input[@placeholder='Enter Your UserName']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@placeholder='Enter Your Password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(.,'Sign in')]")
    private WebElement signInButton;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Execution Started");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Pre-conditions");
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws IOException {
        initializeDriver();
    }

    /**
     * Creates the browser only once per test class. The same method is used
     * by beforeMethod as a safeguard when TestNG configuration ordering or
     * a suite configuration prevents the normal beforeClass hook.
     */
    private void initializeDriver() throws IOException {
        if (driver != null) {
            return;
        }

        String browser = PropertyFileUtility.getData("browser");

        if (!browser.equalsIgnoreCase("chrome")) {
            throw new IllegalArgumentException(
                "Unsupported browser: " + browser
                    + ". This framework currently supports Chrome."
            );
        }

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-features=PasswordLeakDetection");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() throws IOException {
        initializeDriver();
        dismissAlertIfPresent();
        driver.get(PropertyFileUtility.getData("url"));
        dismissAlertIfPresent();
    }

    // Login using credentials from TestDataProvider
    public void loginToApplication(
            String username, String password) {

        if (driver == null || wait == null) {
            throw new IllegalStateException(
                "WebDriver or WebDriverWait is not initialized."
            );
        }

        dismissAlertIfPresent();

        wait.until(
            ExpectedConditions.visibilityOf(usernameField)
        );

        usernameField.clear();
        usernameField.sendKeys(username);

        passwordField.clear();
        passwordField.sendKeys(password);

        wait.until(
            ExpectedConditions.elementToBeClickable(signInButton)
        );

        signInButton.click();

        // Verify successful login
        wait.until(
            ExpectedConditions.urlContains("/dashboard")
        );

        System.out.println(
            "Login successful: " + driver.getCurrentUrl()
        );
    }

    // Login using credentials from properties file
    public void loginToApplication() throws IOException {

        String username =
            PropertyFileUtility.getData("username");

        String password =
            PropertyFileUtility.getData("password");

        loginToApplication(username, password);
    }

    // Handle JavaScript alerts when present
    private void dismissAlertIfPresent() {

        if (driver == null) {
            return;
        }

        try {
            WebDriverWait alertWait = new WebDriverWait(
                driver, Duration.ofSeconds(2)
            );

            Alert alert = alertWait.until(
                ExpectedConditions.alertIsPresent()
            );

            System.out.println(
                "Alert detected: " + alert.getText()
            );

            alert.accept();

        } catch (TimeoutException e) {
            // No alert appeared.
        }
    }

    // Optional helper for JavaScript change-password alerts
    protected void handleChangePasswordPopup() {

        try {
            WebDriverWait alertWait = new WebDriverWait(
                driver, Duration.ofSeconds(3)
            );

            Alert alert = alertWait.until(
                ExpectedConditions.alertIsPresent()
            );

            System.out.println(
                "JavaScript alert: " + alert.getText()
            );

            alert.accept();

        } catch (TimeoutException e) {
            System.out.println(
                "No JavaScript alert appeared."
            );
        }
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        System.out.println("Test Method Completed");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {

        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        System.out.println("Post-condition");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        System.out.println("Execution Completed");
    }
}
