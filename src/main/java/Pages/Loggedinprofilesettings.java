package Pages;

import Base.BasePage;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class Loggedinprofilesettings extends BasePage {

    @AndroidFindBy(accessibility = "Profile Settings")
    private WebElement profileSettingsTitle;

    @AndroidFindBy(accessibility = "Name")
    private WebElement nameField;

    @AndroidFindBy(accessibility = "Save")
    private WebElement saveButton;


    public Loggedinprofilesettings(AndroidDriver driver) {

        super(driver);
    }


    public boolean isProfileSettingsDisplayed() {

        return isDisplayed(profileSettingsTitle);
    }


    public void enterName(String name) {

        enterText(
                nameField,
                name
        );
    }


    public void clickSave() {

        click(saveButton);
    }
}