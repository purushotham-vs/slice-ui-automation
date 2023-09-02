package com.slice.driverutils;

import com.slice.helper.ParentExtender;

/**
 * Created by Purushotham
 */
public class GlobalParams extends ParentExtender {
    private static ThreadLocal<String> platformName = new ThreadLocal<String>();
    private static ThreadLocal<String> udid = new ThreadLocal<String>();
    private static ThreadLocal<String> deviceName = new ThreadLocal<String>();
    private static ThreadLocal<String> systemPort = new ThreadLocal<String>();
    private static ThreadLocal<String> chromeDriverPort = new ThreadLocal<String>();
    private static ThreadLocal<String> wdaLocalPort = new ThreadLocal<String>();
    private static ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<String>();

    public String getPlatformName() {
        return platformName.get();
    }

    public void setPlatformName(String platformName1) {
        platformName.set(platformName1);
    }

    public String getUDID() {
        return udid.get();
    }

    public void setUDID(String udid2) {
        udid.set(udid2);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName2) {
        deviceName.set(deviceName2);
    }

    public String getSystemPort() {
        return systemPort.get();
    }

    public void setSystemPort(String systemPort2) {
        systemPort.set(systemPort2);
    }

    public String getChromeDriverPort() {
        return chromeDriverPort.get();
    }

    public void setChromeDriverPort(String chromeDriverPort2) {
        chromeDriverPort.set(chromeDriverPort2);
    }

    public String getWdaLocalPort() {
        return wdaLocalPort.get();
    }

    public void setWdaLocalPort(String wdaLocalPort2) {
        wdaLocalPort.set(wdaLocalPort2);
    }

    public String getWebkitDebugProxyPort() {
        return webkitDebugProxyPort.get();
    }

    public void setWebkitDebugProxyPort(String webkitDebugProxyPort2) {
        webkitDebugProxyPort.set(webkitDebugProxyPort2);
    }

    public void initializeGlobalParams() {
        GlobalParams params = new GlobalParams();
        params.setPlatformName(deviceProperties.getProperty("platformName"));
        params.setUDID(deviceProperties.getProperty("deviceID"));
        params.setDeviceName(deviceProperties.getProperty("deviceName"));

        switch (params.getPlatformName()) {
            case "Android":
                params.setSystemPort(deviceProperties.getProperty("systemPort"));
                params.setChromeDriverPort(deviceProperties.getProperty("chromeDriverPort"));
                break;
            case "iOS":
                params.setWdaLocalPort(deviceProperties.getProperty("wdaLocalPort"));
                params.setWebkitDebugProxyPort(deviceProperties.getProperty("webkitDebugProxyPort"));
                break;
            default:
                throw new IllegalStateException("Invalid Platform Name!");
        }
    }
}
