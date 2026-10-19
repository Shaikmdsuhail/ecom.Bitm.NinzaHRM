
package ecom.Bitm.NinzaHRM.pomPages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Projects_Page {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public Projects_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ---------------- PAGE ELEMENTS ----------------

    @FindBy(xpath = "//div[@class='table-title']//h2")
    private WebElement projectsHeading;

    @FindBy(xpath = "//button[contains(normalize-space(.),'Create Project')]")
    private WebElement createProjectButton;

    @FindBy(xpath = "//select[option[contains(.,'Project Id')]]")
    private WebElement searchTypeDropdown;

    @FindBy(xpath = "//input[@placeholder='Search by Project Id']")
    private WebElement searchField;

    @FindBy(name = "projectName")
    private WebElement projectNameField;

    @FindBy(name = "createdBy")
    private WebElement projectManagerField;

    @FindBy(xpath = "//input[@value='Add Project']")
    private WebElement addProjectButton;

    // ---------------- BASIC PAGE METHODS ----------------

    public boolean isProjectsHeadingDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOf(projectsHeading))
                    .isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isCreateProjectButtonDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOf(createProjectButton))
                    .isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isSearchFieldDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOf(searchField))
                    .isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isProjectNameFieldDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOf(projectNameField))
                    .isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ---------------- CREATE PROJECT ----------------

    public void clickCreateProject() {
        wait.until(ExpectedConditions.elementToBeClickable(
                createProjectButton)).click();
    }

    public void enterProjectName(String projectName) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOf(projectNameField));

        field.clear();

        if (projectName != null && !projectName.isEmpty()) {
            field.sendKeys(projectName);
        }
    }

    public void enterProjectManager(String managerName) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOf(projectManagerField));

        field.clear();

        if (managerName != null && !managerName.isEmpty()) {
            field.sendKeys(managerName);
        }
    }

    public void selectProjectStatus(String status) {
        WebElement statusDropdown = wait.until(d -> {
            List<WebElement> dropdowns =
                    d.findElements(By.tagName("select"));

            for (WebElement dropdown : dropdowns) {
                if (!dropdown.isDisplayed() || !dropdown.isEnabled()) {
                    continue;
                }

                Select select = new Select(dropdown);

                for (WebElement option : select.getOptions()) {
                    if (option.getText().trim()
                            .equalsIgnoreCase(status)) {
                        return dropdown;
                    }
                }
            }

            return null;
        });

        new Select(statusDropdown).selectByVisibleText(status);
    }

    public void clickAddProject() {
        wait.until(ExpectedConditions.elementToBeClickable(
                addProjectButton)).click();
    }

    // ---------------- SEARCH PROJECT ----------------

    public void searchProject(String projectId) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOf(searchField));

        field.clear();
        field.sendKeys(projectId);
    }

    public String getProjectIdByName(String projectName) {
        WebElement row = getProjectRowByName(projectName);

        return row.findElement(By.xpath("./td[1]"))
                .getText().trim();
    }

    // ---------------- DYNAMIC LOCATORS ----------------

    /*
     * Table columns:
     * td[1] = Project ID
     * td[2] = Project Name
     */

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        String[] parts = value.split("'", -1);
        StringBuilder result = new StringBuilder("concat(");

        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                result.append(", \"'\", ");
            }

            result.append("'").append(parts[i]).append("'");
        }

        result.append(")");
        return result.toString();
    }

    private By projectRowByNameLocator(String projectName) {
        return By.xpath(
                "//tr[td[2][normalize-space(.)="
                        + xpathLiteral(projectName) + "]]");
    }

    private By projectRowByIdLocator(String projectId) {
        return By.xpath(
                "//tr[td[1][normalize-space(.)="
                        + xpathLiteral(projectId) + "]]");
    }

    private WebElement getProjectRowByName(String projectName) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                projectRowByNameLocator(projectName)));
    }

    private WebElement getProjectRowById(String projectId) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                projectRowByIdLocator(projectId)));
    }

    public boolean isProjectDisplayed(String projectName) {
        try {
            return getProjectRowByName(projectName).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean isProjectIdDisplayed(String projectId) {
        try {
            return getProjectRowById(projectId).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    // ---------------- EDIT PROJECT ----------------




public void clickEditProject(String projectName, String updatedName) {

    // Find the exact project row.
    WebElement row = getProjectRowByName(projectName);

    // Click Edit only for that project.
    WebElement editLink = row.findElement(
            By.xpath(".//a[@class='edit']"));

    wait.until(
            ExpectedConditions.elementToBeClickable(editLink)).click();

    // Wait until the Edit modal is visible.
    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("editProjectModal")));

    // Locate and update the Project Name.
    WebElement projectNameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//input[@class='form-control'])[2]")));

    projectNameField.clear();
    projectNameField.sendKeys(updatedName);
}




public void clickUpdateProject() {
    By saveButtonLocator = By.xpath(
            "//input[@value='Save']");

    wait.until(ExpectedConditions.elementToBeClickable(
            saveButtonLocator)).click();

    // Wait until the modal closes after saving.
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.id("editProjectModal")));
}



    // ---------------- DELETE PROJECT ----------------


public String clickDeleteProject(String projectName) {
    WebElement row = getProjectRowByName(projectName);

    WebElement deleteLink = row.findElement(
            By.xpath(".//a[@class='delete']"));

    wait.until(
            ExpectedConditions.elementToBeClickable(deleteLink)).click();

    // Wait for the HTML confirmation modal.
    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(normalize-space(.),"
                    + "'Are you sure you want to delete')]")));

    return projectName;
}

public void confirmDeleteIfPresent(String projectName) {
    WebElement deleteButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[normalize-space()='Delete']"
                    + " | //input[@value='Delete']")));

    deleteButton.click();

    // Wait until the selected project is removed from the table.
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            projectRowByNameLocator(projectName)));
}

public void waitUntilProjectDeleted(String projectName) {
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            projectRowByNameLocator(projectName)));
}

}
