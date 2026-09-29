package Driverfactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class AppiumServerManager {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 4723;

    private static final String APPIUM_URL =
            "http://" + HOST + ":" + PORT;

    private static final int STARTUP_TIMEOUT_SECONDS = 120;

    private static Process appiumProcess;

    private static boolean startedByFramework = false;


    public static void startServer() {

        System.out.println("======================================");
        System.out.println("Checking Appium Server");
        System.out.println("======================================");

        if (isServerRunning()) {

            System.out.println(
                    "Appium Server is already running."
            );

            System.out.println(
                    "URL: " + APPIUM_URL
            );

            return;
        }

        System.out.println(
                "Appium Server is not running."
        );

        System.out.println(
                "Starting Appium Server..."
        );

        startAppiumProcess();

        waitForServer();

        System.out.println(
                "Appium Server started successfully."
        );

        System.out.println(
                "URL: " + APPIUM_URL
        );
    }

    public static boolean isServerRunning() {

        try {

            URL url =
                    URI.create(
                            APPIUM_URL + "/status"
                    ).toURL();

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setConnectTimeout(2000);

            connection.setReadTimeout(2000);

            int responseCode =
                    connection.getResponseCode();

            connection.disconnect();

            return responseCode == 200;

        } catch (Exception e) {

            return false;
        }
    }


    private static void startAppiumProcess() {

        try {

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "cmd.exe",
                            "/c",
                            "appium",
                            "--address",
                            HOST,
                            "--port",
                            String.valueOf(PORT),
                            "--use-drivers",
                            "uiautomator2"
                    );

            processBuilder.redirectErrorStream(true);

            appiumProcess =
                    processBuilder.start();

            startedByFramework = true;


            Thread outputThread =
                    new Thread(() -> {

                        try {

                            BufferedReader reader =
                                    new BufferedReader(
                                            new InputStreamReader(
                                                    appiumProcess.getInputStream()
                                            )
                                    );

                            String line;

                            while ((line = reader.readLine()) != null) {

                                System.out.println(
                                        "[APPIUM] " + line
                                );
                            }

                        } catch (IOException e) {

                            System.out.println(
                                    "Appium output stopped."
                            );
                        }

                    });

            outputThread.setDaemon(true);

            outputThread.start();


        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to start Appium Server. " +
                            "Make sure 'appium' command works in CMD.",
                    e
            );
        }
    }


    private static void waitForServer() {

        System.out.println(
                "Waiting for Appium Server..."
        );

        long startTime =
                System.currentTimeMillis();


        while (true) {


            if (isServerRunning()) {

                return;
            }



            if (appiumProcess != null
                    && !appiumProcess.isAlive()) {

                throw new RuntimeException(
                        "Appium process stopped unexpectedly. "
                                + "Check the [APPIUM] logs above."
                );
            }


            long elapsed =
                    (System.currentTimeMillis()
                            - startTime) / 1000;


            if (elapsed >= STARTUP_TIMEOUT_SECONDS) {

                throw new RuntimeException(
                        "Appium Server did not start within "
                                + STARTUP_TIMEOUT_SECONDS
                                + " seconds."
                );
            }


            sleep(1000);
        }
    }


    public static void stopServer() {

        if (startedByFramework
                && appiumProcess != null
                && appiumProcess.isAlive()) {

            System.out.println(
                    "Stopping Appium Server..."
            );

            appiumProcess.destroy();

            startedByFramework = false;

            System.out.println(
                    "Appium Server stopped."
            );
        }
    }


    private static void sleep(long milliseconds) {

        try {

            Thread.sleep(milliseconds);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "Thread interrupted.",
                    e
            );
        }
    }


}
