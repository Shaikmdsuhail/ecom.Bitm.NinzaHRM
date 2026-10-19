package ecom.Bitm.NinzaHRM.settings;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ecom.Bitm.NinzaHRM.baseFile.BaseClass01;
import ecom.Bitm.NinzaHRM.pomPages.Home_Page;
import ecom.Bitm.NinzaHRM.pomPages.Login_Page;
import ecom.Bitm.NinzaHRM.pomPages.Settings_Page;
import java.io.IOException;
import ecom.Bitm.NinzaHRM.genericUtility.PropertyFileUtility;
import org.testng.Reporter;

public class Settings_Test extends BaseClass01 {

	@BeforeMethod(alwaysRun = true)
	public void loginBeforeTest() throws IOException {
		
		String username = PropertyFileUtility.getData("username");
	    String password = PropertyFileUtility.getData("password");

		Login_Page login = new Login_Page(driver);
		login.loginToApplication(username,password );

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.urlContains("/dashboard"));
	}

	// Navigate to Settings page
	private Settings_Page openSettingsPage() {

		Home_Page home = new Home_Page(driver);
		home.clickSettings();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.urlContains("/dashboard/settings"));

		return new Settings_Page(driver);
	}

	@Test(priority = 1, groups = "smoke")
	public void verifySettingsPageNavigation() {

		Settings_Page settings = openSettingsPage();

		Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard/settings"),
				"Settings page should open successfully.");
		Reporter.log("TC_021 - Verify navigation to Settings page", true);
	}


	@Test(priority = 4, groups = "functional")
	public void verifyGenerateTokenButtonDisplayed() {

		Settings_Page settings = openSettingsPage();

		Assert.assertTrue(settings.isGenerateTokenButtonDisplayed(), "Generate New Token button should be visible.");
		Reporter.log("TC_024 - Verify Generate New Token button is displayed", true);
	}

	@Test(priority = 5, groups = "functional")
	public void verifyAuthorizationCheckboxesDisplayed() {

		Settings_Page settings = openSettingsPage();

		Assert.assertEquals(settings.getAuthorizationCheckboxCount(), 12,
				"There should be 12 authorization checkboxes.");
		Reporter.log("TC_025 - Verify authorization checkboxes are displayed", true);
	}

	@Test(priority = 6, groups = "integration")
	public void verifyGenerateNewToken() {

		Settings_Page settings = openSettingsPage();

		String oldToken = settings.getBearerToken();

		settings.clickGenerateNewToken();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(d -> {
			String newToken = settings.getBearerToken();
			return newToken != null && !newToken.isBlank() && !newToken.equals(oldToken);
		});

		Assert.assertNotEquals(settings.getBearerToken(), oldToken, "A new token should be generated.");
		Reporter.log("TC_026 - Verify a new bearer token can be generated successfully", true);
	}
}