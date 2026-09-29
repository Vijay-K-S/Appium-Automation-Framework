import Driverfactory.AndroidDriverFactory;
import Driverfactory.AppiumServerManager;
import Driverfactory.EmulatorManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;

public class BaseTest {

    protected AndroidDriver driver;

    @BeforeSuite
    public void startTestEnvironment() {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "STARTING MOBILE AUTOMATION ENVIRONMENT"
        );

        System.out.println(
                "======================================"
        );


        EmulatorManager.startEmulator();

        AppiumServerManager.startServer();
    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {

        System.out.println("Creating AndroidDriver session...");

        AndroidDriverFactory driverFactory = new AndroidDriverFactory();

        driver = driverFactory.createDriver();

        System.out.println("AndroidDriver session created.");
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {

            System.out.println(
                    "Closing AndroidDriver session..."
            );

            driver.quit();

            driver = null;
        }
    }


    @AfterSuite
    public void stopTestEnvironment() {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "STOPPING MOBILE AUTOMATION ENVIRONMENT"
        );

        System.out.println(
                "======================================"
        );


        AppiumServerManager.stopServer();
    }
}