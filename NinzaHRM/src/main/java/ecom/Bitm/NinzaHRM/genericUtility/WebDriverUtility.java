package ecom.Bitm.NinzaHRM.genericUtility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.io.FileHandler;

/** Common browser helpers used by page objects and tests. */
public class WebDriverUtility {
    public WebDriverWait waitForElement(WebDriver driver) {
        int seconds = Integer.parseInt(
            PropertyFileUtility.getDataOrDefault("explicitWaitSeconds", "10"));
        return new WebDriverWait(driver, Duration.ofSeconds(seconds));
    }

    public void refreshPage(WebDriver driver) {
        driver.navigate().refresh();
    }

    public void takingScreenshot(String fileName, WebDriver driver) throws IOException {
        Path screenshotDirectory = Path.of("screenshots");
        Files.createDirectories(screenshotDirectory);
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = screenshotDirectory.resolve(fileName + ".png").toFile();
        FileHandler.copy(source, destination);
    }
}
