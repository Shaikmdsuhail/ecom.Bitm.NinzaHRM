
package ecom.Bitm.NinzaHRM.projects;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ecom.Bitm.NinzaHRM.baseFile.BaseClass01;
import ecom.Bitm.NinzaHRM.dataProvider.TestDataProvider;
import ecom.Bitm.NinzaHRM.genericUtility.PropertyFileUtility;
import ecom.Bitm.NinzaHRM.pomPages.Home_Page;
import ecom.Bitm.NinzaHRM.pomPages.Login_Page;
import ecom.Bitm.NinzaHRM.pomPages.Projects_Page;
import org.testng.Reporter;

public class Projects_Test extends BaseClass01 {

    @BeforeMethod(alwaysRun = true)
    public void loginBeforeTest() throws IOException {
        Login_Page login = new Login_Page(driver);

        login.loginToApplication(
                PropertyFileUtility.getData("username"),
                PropertyFileUtility.getData("password"));

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    // ---------------- COMMON METHODS ----------------

    private Projects_Page openProjectsPage() {
        Home_Page home = new Home_Page(driver);
        home.clickProjects();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains("project"));

        return new Projects_Page(driver);
    }

    private String uniqueProjectName(String prefix) {
        return prefix + System.currentTimeMillis();
    }

    private void createProject(
            Projects_Page projects,
            String projectName,
            String managerName,
            String status) {

        projects.clickCreateProject();
        projects.enterProjectName(projectName);
        projects.enterProjectManager(managerName);
        projects.selectProjectStatus(status);
        projects.clickAddProject();

        Assert.assertTrue(
                projects.isProjectDisplayed(projectName),
                "Project was not created: " + projectName);
    }

    // ---------------- SMOKE TEST ----------------

    @Test(priority = 1, groups = {"smoke"})
    public void verifyProjectsPageNavigation() {
        Projects_Page projects = openProjectsPage();

        Assert.assertTrue(
                projects.isProjectsHeadingDisplayed(),
                "Projects page heading should be displayed.");
        Reporter.log("TC_013 - Verify navigation to Projects page", true);
    }

    // ---------------- PAGE ELEMENTS TEST ---------------

    // ---------------- CREATE PROJECT TEST ----------------

    @Test(
        priority = 3,
        dataProvider = "projectData",
        dataProviderClass = TestDataProvider.class,
        groups = {"functional"}
    )
    public void verifyCreateNewProject(
            String projectName,
            String managerName,
            String status) {

        Projects_Page projects = openProjectsPage();

        String uniqueName = uniqueProjectName(projectName);

        createProject(projects, uniqueName, managerName, status);

Reporter.log("TC_015 - Verify a new project can be created with valid data", true);
    }

    // ---------------- NEGATIVE TEST ----------------

    @Test(priority = 4, groups = {"negative"})
    public void verifyProjectCannotBeCreatedWithBlankName() {
        Projects_Page projects = openProjectsPage();

        projects.clickCreateProject();
        projects.enterProjectName("");
        projects.enterProjectManager("");
        projects.clickAddProject();

        Assert.assertTrue(
                projects.isProjectNameFieldDisplayed(),
                "Project form should remain available after blank-name submission.");
        Reporter.log("TC_016 - Verify project creation is prevented when project name is blank", true);
    }

    // ---------------- SYSTEM TEST ----------------

    @Test(priority = 5, groups = {"system"})
    public void verifyProjectsPageAfterRefresh() {
        Projects_Page projects = openProjectsPage();

        String originalUrl = projects.getCurrentUrl();

        driver.navigate().refresh();

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlToBe(originalUrl));

        Projects_Page refreshedPage = new Projects_Page(driver);

        Assert.assertTrue(
                refreshedPage.isProjectsHeadingDisplayed(),
                "Projects heading should be displayed after refresh.");
        Reporter.log("TC_017 - Verify Projects page remains accessible after refresh", true);
    }

    // ---------------- SEARCH TEST ----------------

    @Test(priority = 6, groups = {"functional"})
    public void verifySearchProject() {
        Projects_Page projects = openProjectsPage();

        String projectName = uniqueProjectName("SearchProject");

        createProject(projects, projectName, "Arvind", "Created");

        String projectId = projects.getProjectIdByName(projectName);

        Assert.assertFalse(
                projectId.isEmpty(),
                "Created project should have a project ID.");

        projects.searchProject(projectId);

        Assert.assertTrue(
                projects.isProjectIdDisplayed(projectId),
                "Search should display project ID: " + projectId);
        Reporter.log("TC_018 - Verify project search returns the matching project", true);
    }

    // ---------------- EDIT TEST ----------------



@Test(priority = 7, groups = {"functional"})
public void verifyEditProject() {
    Projects_Page projects = openProjectsPage();

    String originalName = uniqueProjectName("EditProject");
    String updatedName = uniqueProjectName("UpdatedProject");

    createProject(projects, originalName, "Arvind", "Created");

    projects.clickEditProject(originalName, updatedName);
    projects.clickUpdateProject();

    Assert.assertTrue(
            projects.isProjectDisplayed(updatedName),
            "Updated project should be displayed: " + updatedName);
    Reporter.log("TC_019 - Verify an existing project can be edited successfully", true);
}


    // ---------------- DELETE TEST ----------------


@Test(priority = 8, groups = {"functional"})
public void verifyDeleteProject() {
    Projects_Page projects = openProjectsPage();

    String projectName = uniqueProjectName("DeleteProject");

    createProject(projects, projectName, "Arvind", "Created");

    String deletedProject = projects.clickDeleteProject(projectName);
    projects.confirmDeleteIfPresent(deletedProject);

    Assert.assertFalse(
            projects.isProjectDisplayed(deletedProject),
            "Deleted project should no longer be displayed.");

Reporter.log("TC_020 - Verify an existing project can be deleted successfully", true);
}
}
