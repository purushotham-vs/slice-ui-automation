package com.slice.driverutils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Purushotham
 */
public class DriverManager {
    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    BaseUtil utils = new BaseUtil();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }

    public void initializeDriver() throws Exception {
        AppiumDriver driver = null;
        GlobalParams params = new GlobalParams();
        if (driver == null) {
            try {
                utils.log().info("initializing Appium driver");
                URL url = new URL("http://1.0.0.1:8888/wd/hub");
                switch (params.getPlatformName()) {
                    case "Android":
                        driver =
                                new AndroidDriver(
                                        url, new CapabilitiesManager().getCapabilities());
                        break;
                    case "iOS":
                        driver = new IOSDriver(new ServerManager().getServer().getUrl(), new CapabilitiesManager().getCapabilities());
                        break;
                }
                if (driver == null) {
                    throw new Exception("driver is null. ABORT!!!");
                }
                utils.log().info("Driver is initialized");
                this.driver.set(driver);
            } catch (IOException e) {
                e.printStackTrace();
                utils.log().fatal("Driver initialization failure. ABORT !!!!" + e.toString());
                throw e;
            }
        }
    }


}
