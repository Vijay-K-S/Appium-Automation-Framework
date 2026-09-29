package Driverfactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmulatorManager {

    private static final String DEVICE_NAME = "emulator-5554";

    private static final String AVD_NAME = "Vjs_2";

    private static final int BOOT_TIMEOUT_SECONDS = 120;


    public static void startEmulator() {

        System.out.println("======================================");
        System.out.println("Checking Android Emulator");
        System.out.println("======================================");

        if (isEmulatorRunning()) {

            System.out.println(
                    "Emulator is already running: "
                            + DEVICE_NAME
            );

        } else {

            System.out.println(
                    "Emulator is not running."
            );

            System.out.println(
                    "Starting AVD: " + AVD_NAME
            );

            startEmulatorProcess();

            waitForEmulator();
        }

        waitForBootCompleted();

        System.out.println(
                "Android Emulator is ready."
        );
    }


    private static boolean isEmulatorRunning() {

        try {

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "adb",
                            "devices"
                    );

            Process process =
                    processBuilder.start();

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith(DEVICE_NAME)
                        && line.contains("device")) {

                    return true;
                }
            }

            process.waitFor();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to execute ADB. " +
                            "Make sure Android SDK platform-tools is in PATH.",
                    e
            );
        }

        return false;
    }


    private static void startEmulatorProcess() {

        try {

            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            "emulator",
                            "-avd",
                            AVD_NAME,
                            "-no-snapshot"
                    );

            processBuilder.redirectErrorStream(true);

            Process process =
                    processBuilder.start();


            Thread outputThread =
                    new Thread(() -> {

                        try {

                            BufferedReader reader =
                                    new BufferedReader(
                                            new InputStreamReader(
                                                    process.getInputStream()
                                            )
                                    );

                            String line;

                            while ((line = reader.readLine()) != null) {

                                System.out.println(
                                        "[EMULATOR] " + line
                                );
                            }

                        } catch (IOException e) {

                            System.out.println(
                                    "Emulator output stopped."
                            );
                        }

                    });

            outputThread.setDaemon(true);

            outputThread.start();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to start Android Emulator. " +
                            "Make sure 'emulator' is available in PATH.",
                    e
            );
        }
    }


    private static void waitForEmulator() {

        System.out.println(
                "Waiting for emulator to become available..."
        );

        long startTime =
                System.currentTimeMillis();


        while (true) {

            if (isEmulatorRunning()) {

                System.out.println(
                        "ADB detected emulator: "
                                + DEVICE_NAME
                );

                return;
            }


            long elapsed =
                    (System.currentTimeMillis()
                            - startTime) / 1000;


            if (elapsed >= BOOT_TIMEOUT_SECONDS) {

                throw new RuntimeException(
                        "Emulator was not detected within "
                                + BOOT_TIMEOUT_SECONDS
                                + " seconds."
                );
            }

            sleep(2000);
        }
    }


    private static void waitForBootCompleted() {

        System.out.println(
                "Waiting for Android boot to complete..."
        );

        long startTime =
                System.currentTimeMillis();


        while (true) {

            try {

                ProcessBuilder processBuilder =
                        new ProcessBuilder(
                                "adb",
                                "-s",
                                DEVICE_NAME,
                                "shell",
                                "getprop",
                                "sys.boot_completed"
                        );

                Process process =
                        processBuilder.start();


                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        process.getInputStream()
                                )
                        );


                String result =
                        reader.readLine();


                process.waitFor();


                if (result != null
                        && result.trim().equals("1")) {

                    System.out.println(
                            "Android boot completed."
                    );

                    return;
                }

            } catch (Exception e) {

                System.out.println(
                        "Android is still booting..."
                );
            }


            long elapsed =
                    (System.currentTimeMillis()
                            - startTime) / 1000;


            if (elapsed >= BOOT_TIMEOUT_SECONDS) {

                throw new RuntimeException(
                        "Android Emulator boot timed out."
                );
            }

            sleep(2000);
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