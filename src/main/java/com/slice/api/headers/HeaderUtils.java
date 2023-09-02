package com.slice.api.headers;

import com.slice.CommonConstants;

import java.util.HashMap;
import java.util.Map;

public class HeaderUtils {
    public static Map<String, String> getHeader(String sessionToken, String appVersion, String appVersionCode, String deviceId) {
        Map<String, String> headers = new HashMap<>();
        headers.put(CommonConstants.CONTENT_TYPE_KEYWORD, CommonConstants.CONTENT_TYPE_JSON);
        headers.put("appVersion", appVersion);
        headers.put("appVersionCode", appVersionCode);
        headers.put("X-Session-Token", sessionToken);
        headers.put("deviceId", deviceId);
        headers.put("osVersion", "ANDROID_12");
        headers.put("X-Target", "LE");
        headers.put("source", "APK");
        return headers;
    }
    }
}
