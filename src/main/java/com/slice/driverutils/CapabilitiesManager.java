package com.slice.driverutils;

import com.slice.helper.ParentExtender;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

/**
 * Created by Purushotham
 */
public class CapabilitiesManager extends ParentExtender {
    BaseUtil utils = new BaseUtil();

    public DesiredCapabilities getCapabilities() throws IOException {
        GlobalParams params = new GlobalParams();
        try {
            utils.log().info("getting capabilities");
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, params.getPlatformName());
            caps.setCapability(MobileCapabilityType.UDID, params.getUDID());
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, params.getDeviceName());
            switch (params.getPlatformName()) {
                case "Android":
                    caps.setCapability(
                            MobileCapabilityType.AUTOMATION_NAME, deviceProperties.getProperty("androidAutomationName"));
                    caps.setCapability("appPackage", deviceProperties.getProperty("androidAppPackage"));
                    caps.setCapability("appActivity", deviceProperties.getProperty("androidAppActivity"));
                    caps.setCapability("newCommandTimeout", 600);
                    caps.setCapability("systemPort", params.getSystemPort());
                    //caps.setCapability("chromeDriverPort", params.getChromeDriverPort());
                    caps.setCapability("noReset", false);
                    caps.setCapability("fullReset", true);
                    caps.setCapability("dontStopAppOnReset", false);
                    String androidAppUrl ="/Users/dilipp/Documents/Project/slice-ui-automation/src/test/resources/apps/appVersion/ap/app-qa-debug-universal.apk";
                    utils.log().info("appUrl is" + androidAppUrl);
                    caps.setCapability("app", androidAppUrl);
                    break;
                case "iOS":
                    caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, deviceProperties.getProperty("iOSAutomationName"));
                    caps.setCapability("bundleId", deviceProperties.getProperty("iOSBundleId"));
                    caps.setCapability("wdaLocalPort", params.getWdaLocalPort());
                    caps.setCapability("app", deviceProperties.getProperty("iOSAppUrl"));
                    caps.setCapability("updatedWDABundleId", "io.appium.WebDriverAgent");
                    break;
            }
            return caps;
        } catch (Exception e) {
            e.printStackTrace();
            utils.log().fatal("Failed to load capabilities. ABORT!!" + e.toString());
            throw e;
        }
    }
}