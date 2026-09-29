package Pages;

import Base.BasePage;
import Utils.Waits;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class SigninPage extends BasePage {

    @AndroidFindBy(accessibility = "Email")
    private WebElement emailField;

    @AndroidFindBy(accessibility = "Password")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "Sign in")
    private WebElement signInButton;



    private Waits waits;
    public SigninPage(AndroidDriver driver) {

        super(driver);

        waits = new Waits(driver);
    }


    public void enterEmail(String email) {

        enterText(
                emailField,
                email
        );
    }


    public void enterPassword(String password) {

        enterText(
                passwordField,
                password
        );
    }


    public EventsPage clickSignIn() {

        click(signInButton);

        return new EventsPage(driver);
    }


    public EventsPage login(
            String email,
            String password) {

        enterEmail(email);

        enterPassword(password);

        return clickSignIn();
    }


}