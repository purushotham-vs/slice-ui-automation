package com.slice.helper;

import com.slice.CommonConstants;
import com.slice.driverutils.BaseUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * slice-ui-automation
 */
public class ParentExtender {

    public static Properties deviceProperties=new Properties();

    public ParentExtender() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(CommonConstants.DEVICE_PROPERTIES_FILE_PATH);
            deviceProperties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
