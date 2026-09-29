package Base;

import Utils.MobileActions;
import Utils.Waits;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    protected AndroidDriver driver;
    protected Waits wait;
    protected MobileActions mobileActions;

    public BasePage(AndroidDriver driver) {

        this.driver = driver;
        this.wait = new Waits(driver);
        this.mobileActions = new MobileActions(driver);

        PageFactory.initElements(
                new AppiumFieldDecorator(driver),
                this
        );
    }

    protected void click(WebElement element) {

        wait.waitForElementToBeClickable(element);

        element.click();
    }

    protected void enterText(WebElement element, String text) {

        wait.waitForElementToBeVisible(element);

        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {

        wait.waitForElementToBeVisible(element);

        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {

        try {

            wait.waitForElementToBeVisible(element);

            return element.isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}