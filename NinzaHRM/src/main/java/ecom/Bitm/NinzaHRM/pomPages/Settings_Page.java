package ecom.Bitm.NinzaHRM.pomPages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Settings_Page {

	private WebDriver driver;
	private WebDriverWait wait;

	public Settings_Page(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// Bearer Token field
	@FindBy(xpath = "//input[contains(@value,'eyJ')]")
	private WebElement bearerTokenField;

	// Token Lifespan dropdown
	@FindBy(xpath = "//select")
	private WebElement tokenLifespanDropdown;

	// Generate New Token button
	@FindBy(xpath = "//button[contains(.,'Generate New Token')]")
	private WebElement generateTokenButton;

	// Projects authorization checkboxes
	@FindBy(xpath = "//input[@type='checkbox']")
	private java.util.List<WebElement> authorizationCheckboxes;

	// Verify Bearer Token field
	public boolean isBearerTokenDisplayed() {
		return bearerTokenField.isDisplayed();
	}

	// Verify Token Lifespan dropdown
	public boolean isTokenLifespanDisplayed() {
		return tokenLifespanDropdown.isDisplayed();
	}

	// Verify Generate New Token button
	public boolean isGenerateTokenButtonDisplayed() {
		return generateTokenButton.isDisplayed();
	}

	// Count authorization checkboxes
	public int getAuthorizationCheckboxCount() {
		return authorizationCheckboxes.size();
	}

	// Select Token Lifespan
	public void selectTokenLifespan(String lifespan) {
		wait.until(ExpectedConditions.elementToBeClickable(tokenLifespanDropdown));

		new Select(tokenLifespanDropdown).selectByVisibleText(lifespan);
	}

	// Click Generate New Token
	public void clickGenerateNewToken() {
		wait.until(ExpectedConditions.elementToBeClickable(generateTokenButton));

		generateTokenButton.click();
	}

	// Get displayed token
	public String getBearerToken() {
		return bearerTokenField.getAttribute("value");
	}
}