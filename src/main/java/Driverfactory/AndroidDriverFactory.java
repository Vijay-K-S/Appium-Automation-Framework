package Driverfactory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDriverFactory {


    public AndroidDriver createDriver()
            throws MalformedURLException {


        EmulatorManager.startEmulator();

        AppiumServerManager.startServer();


        UiAutomator2Options options =
                new UiAutomator2Options();


        options.setPlatformName("Android");

        options.setDeviceName("emulator-5554");

        options.setAutomationName("UiAutomator2");

        options.setPlatformVersion("17");

        options.setAppPackage("app.frontrow.qa");

        options.setAppActivity(
                "app.frontrow.qa.MainActivity"
        );

        options.setAutoGrantPermissions(true);

        return new AndroidDriver(
                new URL(
                        "http://127.0.0.1:4723"
                ),
                options
        );
    }
}