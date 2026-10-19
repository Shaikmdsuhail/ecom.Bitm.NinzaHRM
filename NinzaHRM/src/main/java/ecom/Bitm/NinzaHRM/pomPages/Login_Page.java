package ecom.Bitm.NinzaHRM.pomPages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Login_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public Login_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(5));

        PageFactory.initElements(driver, this);
    }

    // Login page elements
    @FindBy(xpath = "//input[@placeholder='Enter Your UserName']")
    private WebElement usernameField;

    @FindBy(xpath = "//input[@placeholder='Enter Your Password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(.,'Sign in')]")
    private WebElement signInButton;

    @FindBy(xpath = "//button[contains(.,'Request For Demo')]")
    private WebElement requestDemoButton;

    // Handle any JavaScript alert that is already open
    private void dismissAlertIfPresent() {
        try {
            Alert alert = driver.switchTo().alert();

            System.out.println(
                    "Existing alert detected: " + alert.getText());

            alert.accept();

            System.out.println("Clicked OK on the existing alert.");

        } catch (NoAlertPresentException e) {
            // No alert is open; continue normally.
        }
    }

    // Enter username
    public void enterUsername(String username) {
        dismissAlertIfPresent();

        wait.until(
                ExpectedConditions.visibilityOf(usernameField));

        usernameField.clear();
        usernameField.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        dismissAlertIfPresent();

        wait.until(
                ExpectedConditions.visibilityOf(passwordField));

        passwordField.clear();
        passwordField.sendKeys(password);
    }

    // Click Sign In
    public void clickSignIn() {
        dismissAlertIfPresent();

        wait.until(
                ExpectedConditions.elementToBeClickable(signInButton));

        signInButton.click();
    }

    // Complete login operation
    public void loginToApplication(
            String username,
            String password) {

        // Prevent a previous negative-test alert from blocking this test.
        dismissAlertIfPresent();

        wait.until(
                ExpectedConditions.visibilityOf(usernameField));

        usernameField.clear();
        usernameField.sendKeys(username);

        passwordField.clear();
        passwordField.sendKeys(password);

        wait.until(
                ExpectedConditions.elementToBeClickable(signInButton));

        signInButton.click();
    }

    // Verify username field
    public boolean isUsernameFieldDisplayed() {
        dismissAlertIfPresent();

        return usernameField.isDisplayed();
    }

    // Verify password field
    public boolean isPasswordFieldDisplayed() {
        dismissAlertIfPresent();

        return passwordField.isDisplayed();
    }

    // Verify password masking
    public boolean isPasswordMasked() {
        dismissAlertIfPresent();

        return "password".equalsIgnoreCase(
                passwordField.getAttribute("type"));
    }

    // Verify Sign In button
    public boolean isSignInButtonDisplayed() {
        dismissAlertIfPresent();

        return signInButton.isDisplayed();
    }

    // Verify Request For Demo button
    public boolean isRequestDemoButtonDisplayed() {
        dismissAlertIfPresent();

        return requestDemoButton.isDisplayed();
    }

    // Get current page title
    public String getCurrentPageTitle() {
        return driver.getTitle();
    }

    // Check whether a JavaScript alert appears
    public boolean isAlertPresent() {
        try {
            wait.until(
                    ExpectedConditions.alertIsPresent());

            return true;

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    // Read alert text
    public String getAlertText() {
        Alert alert = wait.until(
                ExpectedConditions.alertIsPresent());

        return alert.getText();
    }

    // Click OK on the current JavaScript alert
    public void acceptAlert() {
        wait.until(
                ExpectedConditions.alertIsPresent());

        driver.switchTo().alert().accept();

        System.out.println(
                "Clicked OK on the JavaScript alert.");
    }
}