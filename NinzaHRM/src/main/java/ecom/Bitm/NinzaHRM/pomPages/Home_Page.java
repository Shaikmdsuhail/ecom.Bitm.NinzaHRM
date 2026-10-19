package ecom.Bitm.NinzaHRM.pomPages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    public Home_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));

        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//*[contains(text(),'Home')]")
    private WebElement homeMenu;

    @FindBy(xpath = "//*[contains(text(),'Dashboard')]")
    private WebElement dashboardMenu;

    @FindBy(xpath = "//*[contains(text(),'Projects')]")
    private WebElement projectsMenu;

    @FindBy(xpath = "//*[contains(text(),'Employees')]")
    private WebElement employeesMenu;

    @FindBy(xpath = "//*[contains(text(),'Settings')]")
    private WebElement settingsMenu;

    @FindBy(xpath = "//*[contains(text(),'Resources')]")
    private WebElement resourcesMenu;

    @FindBy(xpath = "//*[contains(text(),'About')]")
    private WebElement aboutMenu;

    @FindBy(xpath = "//*[contains(text(),'Contact Admin')]")
    private WebElement contactAdminMenu;

    public boolean isHomeMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(homeMenu)).isDisplayed();
    }

    public boolean isDashboardMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(dashboardMenu)).isDisplayed();
    }

    public boolean isProjectsMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(projectsMenu)).isDisplayed();
    }

    public boolean isEmployeesMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(employeesMenu)).isDisplayed();
    }

    public boolean isSettingsMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(settingsMenu)).isDisplayed();
    }

    public boolean isResourcesMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(resourcesMenu)).isDisplayed();
    }

    public boolean isAboutMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(aboutMenu)).isDisplayed();
    }

    public boolean isContactAdminMenuDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOf(contactAdminMenu)).isDisplayed();
    }

    public void clickDashboard() {
        wait.until(
                ExpectedConditions.elementToBeClickable(dashboardMenu)).click();
    }

    public void clickProjects() {
        wait.until(
                ExpectedConditions.elementToBeClickable(projectsMenu)).click();
    }

    public void clickSettings() {
        wait.until(
                ExpectedConditions.elementToBeClickable(settingsMenu)).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}