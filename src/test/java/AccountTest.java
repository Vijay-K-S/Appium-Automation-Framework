import Pages.EventsPage;
import Pages.Loggedinprofilesettings;
import Pages.ProfilePage;
import Pages.SigninPage;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountTest extends BaseTest {

    private ProfilePage profilePage;
    private SigninPage signinPage;
    private Loggedinprofilesettings loggedinprofilesettings;
    private EventsPage eventsPage;


    @BeforeMethod
    public void initializePages() {

        profilePage = new ProfilePage(driver);
        signinPage = new SigninPage(driver);
        loggedinprofilesettings = new Loggedinprofilesettings(driver);
        eventsPage = new EventsPage(driver);

        profilePage = eventsPage.clickProfile();

        signinPage.enterEmail("demo@frontrow.app");
        signinPage.enterPassword("demo1234");

        eventsPage = signinPage.clickSignIn();


    }


    @Test(priority=0)
    public void editprofile(){

        Assert.assertTrue(profilePage.isProfilePageDisplayed(),"Profile page is not displayed");
        profilePage.clickEditProfileButton();
        profilePage.changeDisplayName("John");
        Assert.assertEquals(
                profilePage.getProfileName(),
                "John",
                "Profile name was not updated correctly"
        );
    }

    @Test(priority=1)
    public void turnOffPushNotification() {

        profilePage.clickSettings();
        profilePage.turnOffPushNotification();
        profilePage.clickBackButton();
        profilePage.isProfilePageDisplayed();
    }

    @AfterMethod
    public void signout(){
           profilePage.accountsignout();
    }

}