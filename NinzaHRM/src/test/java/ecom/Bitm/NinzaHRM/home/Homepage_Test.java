
package ecom.Bitm.NinzaHRM.home;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import ecom.Bitm.NinzaHRM.baseFile.BaseClass01;
import ecom.Bitm.NinzaHRM.dataProvider.TestDataProvider;
import ecom.Bitm.NinzaHRM.login.Loginpage_Test;
import ecom.Bitm.NinzaHRM.pomPages.Home_Page;
import org.testng.Reporter;

public class Homepage_Test extends BaseClass01 {

    // --------------------------------------------------
    // SMOKE TESTING
    // --------------------------------------------------

    @Test(
        priority = 1,
        dataProvider = "loginCredentials",
        dataProviderClass = TestDataProvider.class,
        groups = {"smoke"}
    )
    public void verifyHomePageIsDisplayed(
            String username, String password) throws IOException {

        loginToApplication();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("/dashboard"),
            "Dashboard should be displayed after login."
        );
        Reporter.log("TC_007 - Verify homepage dashboard loads after successful login", true);
    }

    // --------------------------------------------------
    // FUNCTIONAL TESTING
    // --------------------------------------------------

//    @Test(priority = 3, groups = {"functional"})
//    public void verifyDashboardMenusAreDisplayed()
//            throws IOException {
//
//        loginToApplication();
//
//        Home_Page home = new Home_Page(driver);
//
//        Assert.assertTrue(
//            home.isDashboardMenuDisplayed(),
//            "Dashboard menu should be visible."
//        );
//
//        Assert.assertTrue(
//            home.isProjectsMenuDisplayed(),
//            "Projects menu should be visible."
//        );
//
//        Assert.assertTrue(
//            home.isSettingsMenuDisplayed(),
//            "Settings menu should be visible."
//        );
//
//        Assert.assertTrue(
//            home.isHomeMenuDisplayed(),
//            "Home menu should be visible."
//        );
//        Reporter.log("TC_008 - Verify all dashboard menus are displayed", true);
//    }

    // --------------------------------------------------
    // NEGATIVE TESTING
    // --------------------------------------------------

    @Test(priority = 4, groups = {"negative"})
    public void verifyDashboardRequiresAuthentication()
            throws IOException {

        // Login before testing unauthorized access
        loginToApplication();

        String dashboardUrl = driver.getCurrentUrl();

        // Remove authentication cookies
        driver.manage().deleteAllCookies();

        // Clear browser storage
        ((JavascriptExecutor) driver).executeScript(
            "window.localStorage.clear();"
        );

        ((JavascriptExecutor) driver).executeScript(
            "window.sessionStorage.clear();"
        );

        // Attempt to access the protected dashboard
        driver.get(dashboardUrl);

        WebDriverWait authWait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        authWait.until(d ->
            !d.getCurrentUrl().contains("/dashboard")
        );

        Loginpage_Test.takeScreenshot(
            driver,
            "NegativeHome_UnauthenticatedAccess"
        );

        Assert.assertFalse(
            driver.getCurrentUrl().contains("/dashboard"),
            "Unauthenticated user should not access the dashboard."
        );
        Reporter.log("TC_009 - Verify unauthenticated users cannot access dashboard", true);
    }

    // --------------------------------------------------
    // INTEGRATION TESTING
    // --------------------------------------------------

    @Test(priority = 6, groups = {"integration"})
    public void verifyProjectsNavigation() throws IOException {

        loginToApplication();

        Home_Page home = new Home_Page(driver);

        home.clickProjects();

        WebDriverWait navigationWait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        navigationWait.until(
            ExpectedConditions.urlContains("project")
        );

        Assert.assertTrue(
            driver.getCurrentUrl()
                .toLowerCase()
                .contains("project"),
            "Projects page should open successfully."
        );
        Reporter.log("TC_010 - Verify navigation from dashboard to Projects page", true);
    }

    @Test(priority = 7, groups = {"integration"})
    public void verifySettingsNavigation() throws IOException {

        loginToApplication();

        Home_Page home = new Home_Page(driver);

        home.clickSettings();

        WebDriverWait navigationWait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        navigationWait.until(
            ExpectedConditions.urlContains("settings")
        );

        Assert.assertTrue(
            driver.getCurrentUrl()
                .toLowerCase()
                .contains("settings"),
            "Settings page should open successfully."
        );
        Reporter.log("TC_011 - Verify navigation from dashboard to Settings page", true);
    }

    // --------------------------------------------------
    // SYSTEM TESTING
    // --------------------------------------------------

    @Test(priority = 8, groups = {"system"})
    public void verifyDashboardAfterRefresh() throws IOException {

        loginToApplication();

        String dashboardUrl = driver.getCurrentUrl();

        driver.navigate().refresh();

        WebDriverWait refreshWait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        refreshWait.until(
            ExpectedConditions.urlContains("/dashboard")
        );

        Assert.assertTrue(
            driver.getCurrentUrl().contains("/dashboard"),
            "Dashboard should remain accessible after refresh."
        );

        Assert.assertEquals(
            driver.getCurrentUrl(),
            dashboardUrl,
            "Dashboard URL should remain unchanged after refresh."
        );
        Reporter.log("TC_012 - Verify dashboard remains accessible after page refresh", true);
    }
}
