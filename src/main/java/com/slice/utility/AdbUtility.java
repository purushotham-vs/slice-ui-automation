package com.slice.utility;

import com.slice.models.UserDetails;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.testng.Reporter;

public class AdbUtility {

    private final String deviceId;

    public AdbUtility(String deviceId) {
        this.deviceId = deviceId;
    }

    public void adbExecute(String command) {
        Reporter.log("command ..." + command, true);
        ShellCommandExecutor.executeCommands("adb " + command);
    }

    public String getAdbDevices() {
        return ShellCommandExecutor.executeCommands("adb devices");
    }

    public void uninstallApp(String packageName) {
        adbExecute("uninstall " + packageName);
    }

    public void clearData(String packageName) {
        adbExecute("shell pm clear " + packageName);
    }

    public void forceStopApp(String packageName) {
        adbExecute("shell am force-stop " + packageName);
    }

    public String checkDeviceModelName() {
        String adbDeviceLogs = ShellCommandExecutor.executeCommands("adb -s " + deviceId + " shell getprop ro.product.model");
        Reporter.log("Model Name : " + adbDeviceLogs, true);
        return adbDeviceLogs;
    }

    public String checkDeviceOs() {
        String deviceOS = ShellCommandExecutor.executeCommands("adb -s " + deviceId + " shell getprop ro.build.version.release");
        Reporter.log("Device OS Name : " + deviceOS, true);
        return deviceOS;
    }

    public void upgradeApp(String appPackage) {
        String command = "install -r " + appPackage;
        try {
            adbExecute(command);
        } catch (Exception e) {
            Reporter.log(ExceptionUtils.getStackTrace(e), true);
        }
    }

    public String getSessionTokenAndDeviceId() throws InterruptedException {
        GetValueFromLogs getValueFromLogs = new GetValueFromLogs();
        String sessionTokenMessage = getValueFromLogs.getValueFromADBLog("X-Session-Token");
        String deviceIdMessage = getValueFromLogs.getValueFromADBLog("deviceId");
        String[] sessionTokenString = sessionTokenMessage.split(":");
        String[] deviceIdString = deviceIdMessage.split(":");
        String sessionId = sessionTokenString[sessionTokenString.length-1];
        UserDetails.getInstance().setSessionToken(sessionId.trim());
        return sessionId.trim();
    }

    public void killSystemPort(){
        ShellCommandExecutor.executeCommands("adb kill-server");
        ShellCommandExecutor.executeCommands("adb uninstall io.appium.uiautomator2.server");
        ShellCommandExecutor.executeCommands("adb uninstall io.appium.uiautomator2.server.test");
    }
}
