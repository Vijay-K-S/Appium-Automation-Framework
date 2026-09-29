package Pages;

import Base.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class ProfilePage extends BasePage {

    @AndroidFindBy(xpath = "(//android.widget.TextView[@text=\"Profile\"])[1]")
    private WebElement profileTitle;

    @AndroidFindBy(accessibility = "Settings")
    private WebElement settingsButton;

    @AndroidFindBy(accessibility = "Edit profile")
    private WebElement editProfileButton;

    @AndroidFindBy(accessibility = "Display name")
    private WebElement displayName;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text=\"demo@frontrow.app\"]")
    private WebElement profileName;

    @AndroidFindBy(xpath = "//android.widget.Switch[@content-desc=\"Push notifications\"]")
    private WebElement pushNotificationButton;

    @AndroidFindBy(accessibility = "Navigate up")
    private WebElement BackButton;

    @AndroidFindBy(accessibility = "Sign out")
    private WebElement signOutButton;


    public ProfilePage(AndroidDriver driver) {

        super(driver);
    }


    public boolean isProfilePageDisplayed() {

        return isDisplayed(profileTitle);
    }


    public Loggedinprofilesettings clickSettings() {

        click(settingsButton);

        return new Loggedinprofilesettings(driver);
    }


    public void clickEditProfileButton() {

        click(editProfileButton);
    }


    public void changeDisplayName(String newDisplayName) {

        enterText(displayName, newDisplayName);

        click(saveButton);
    }


    public boolean isProfileNameDisplayed() {

        return isDisplayed(profileName);
    }


    public String getProfileName() {

        return getText(profileName);
    }


    public void turnOffPushNotification() {

        mobileActions.tap(pushNotificationButton);
    }

    public void clickBackButton() {
        click(BackButton);
    }

    public void accountsignout(){
        click(signOutButton);
    }
}