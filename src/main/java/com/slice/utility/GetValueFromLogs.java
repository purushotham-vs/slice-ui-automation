package com.slice.utility;

import com.slice.PageUtility;
import org.openqa.selenium.logging.LogEntry;

import java.util.List;

public class GetValueFromLogs extends PageUtility {

    public String getValueFromADBLog(String key) throws InterruptedException {
        Thread.sleep(4000);
        String message = null;
        List<LogEntry> logEntries = PageUtility.getCurrentDriver().manage().logs().get("logcat").getAll();
        for (LogEntry logEntry : logEntries) {
            if (logEntry.getMessage().contains(key)) {
                message = logEntry.getMessage();
                break;
            }
        }
        return message;
    }

}
