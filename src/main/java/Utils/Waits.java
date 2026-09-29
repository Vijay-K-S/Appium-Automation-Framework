package Utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Waits {

    private final WebDriverWait wait;

    public Waits(AndroidDriver driver) {

        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    public void waitForElementToBeVisible(WebElement element) {

        wait.until(
                ExpectedConditions.visibilityOf(element)
        );
    }

    public void waitForElementToBeClickable(WebElement element) {

        wait.until(
                ExpectedConditions.elementToBeClickable(element)
        );
    }

    public void waitForElementToDisappear(WebElement element) {

        wait.until(
                ExpectedConditions.invisibilityOf(element)
        );
    }
}