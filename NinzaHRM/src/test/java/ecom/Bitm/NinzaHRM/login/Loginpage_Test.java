package ecom.Bitm.NinzaHRM.login;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import ecom.Bitm.NinzaHRM.baseFile.BaseClass01;
import ecom.Bitm.NinzaHRM.dataProvider.TestDataProvider;
import ecom.Bitm.NinzaHRM.pomPages.Login_Page;
import org.testng.Reporter;

public class Loginpage_Test extends BaseClass01 {

    // --------------------------------------------------
    // SMOKE TESTING
    // --------------------------------------------------

    @Test(priority = 1, groups = {"smoke"})
    public void verifyLoginPageLoads() {

        Login_Page login = new Login_Page(driver);

        Assert.assertTrue(
                login.isUsernameFieldDisplayed(),
                "Username field should be displayed");

        Assert.assertTrue(
                login.isPasswordFieldDisplayed(),
                "Password field should be displayed");

        Assert.assertTrue(
                login.isSignInButtonDisplayed(),
                "Sign In button should be displayed");
        Reporter.log("TC_001 - Verify login page loads successfully", true);
    }

    // --------------------------------------------------
    // FUNCTIONAL TESTING
    // --------------------------------------------------

    @Test(priority = 2, groups = {"functional"})
    public void verifyPasswordIsMasked() {

        Login_Page login = new Login_Page(driver);

        Assert.assertTrue(
                login.isPasswordMasked(),
                "Password field should mask entered characters");
        Reporter.log("TC_002 - Verify password field masks entered password", true);
    }

    // --------------------------------------------------
    // DATA-DRIVEN LOGIN TESTING
    // Excel supplies valid and invalid credentials.
    // --------------------------------------------------

    @Test(
        priority = 3,
        dataProvider = "loginData",
        dataProviderClass = TestDataProvider.class,
        groups = {"functional","negative"}
    )
    public void verifyLogin(
            String username,
            String password,
            String expectedResult) throws IOException {

        Login_Page login = new Login_Page(driver);

        login.loginToApplication(username, password);

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(5));

        if (expectedResult.equalsIgnoreCase("Success")) {

            wait.until(
                    ExpectedConditions.urlContains("/dashboard"));

            Assert.assertTrue(
                    driver.getCurrentUrl().contains("/dashboard"),
                    "Valid login should navigate to the dashboard");

        } else {

            // Wait for the Invalid Credentials alert.
            Assert.assertTrue(
                    login.isAlertPresent(),
                    "Invalid login should display an alert");

            String alertText = login.getAlertText();

            Assert.assertEquals(
                    alertText,
                    "Invalid Credentials",
                    "Unexpected alert message");

            // Click OK to dismiss the alert.
            login.acceptAlert();

            System.out.println(
                    "Invalid login alert accepted: " + alertText);

            // Capture screenshot after dismissing the alert.
            takeScreenshot(
                    driver,
                    "NegativeLogin_" + safeFileName(username));

            // Verify that login was rejected.
            Assert.assertFalse(
                    driver.getCurrentUrl().contains("/dashboard"),
                    "Invalid credentials must not navigate to the dashboard");
        }
        Reporter.log("TC_003 - Verify login with valid credentials", true);
    }



    // --------------------------------------------------
    // SYSTEM TESTING
    // --------------------------------------------------

    @Test(
        priority = 5,
        dataProvider = "loginData",
        dataProviderClass = TestDataProvider.class,
        groups = {"system"}
    )
    public void verifyLoginSessionAfterRefresh(
            String username,
            String password,
            String expectedResult) {

        if (!expectedResult.equalsIgnoreCase("Success")) {
            return;
        }

        Login_Page login = new Login_Page(driver);

        login.loginToApplication(username, password);

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(5));

        wait.until(
                ExpectedConditions.urlContains("/dashboard"));

        String dashboardUrl = driver.getCurrentUrl();

        driver.navigate().refresh();

        wait.until(
                ExpectedConditions.urlContains("/dashboard"));

        Assert.assertTrue(
                driver.getCurrentUrl().contains("/dashboard"),
                "Dashboard should remain accessible after refresh");

        Assert.assertEquals(
                driver.getCurrentUrl(),
                dashboardUrl,
                "Refresh should retain the current dashboard URL");
        Reporter.log("TC_004, TC_005, TC_006 - Verify login with invalid credentials displays Invalid Credentials alert and blank Credentials and session remains active after refresh", true);
    }

    // --------------------------------------------------
    // SCREENSHOT UTILITY
    // --------------------------------------------------

    public static void takeScreenshot(
            org.openqa.selenium.WebDriver driver,
            String screenshotName) throws IOException {

        File source = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        File destination = new File(
                "screenshots/" + screenshotName + ".png");

        FileUtils.forceMkdirParent(destination);
        FileUtils.copyFile(source, destination);

        System.out.println(
                "Screenshot saved: "
                + destination.getAbsolutePath());
    }

    private String safeFileName(String value) {

        if (value == null || value.trim().isEmpty()
                || value.equalsIgnoreCase("(blank)")) {
            return "BlankCredentials";
        }

        return value.replaceAll("[^a-zA-Z0-9]", "_");
    }
}