package Pages;

import Base.BasePage;
import Utils.Waits;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class EventsPage extends BasePage {

    @AndroidFindBy(accessibility = "Events")
    private WebElement eventsTitle;

    @AndroidFindBy(xpath="//android.view.View[@content-desc=\"Profile tab\"]")
    private WebElement profileButton;


    private Waits waits;
    public EventsPage(AndroidDriver driver) {

        super(driver);
        waits = new Waits(driver);
    }


    public boolean isEventsPageDisplayed() {

        return isDisplayed(eventsTitle);
    }


    public ProfilePage clickProfile() {

        waits.waitForElementToBeClickable(profileButton);

        click(profileButton);

        return new ProfilePage(driver);
    }

}