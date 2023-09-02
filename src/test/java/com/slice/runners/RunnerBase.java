package com.slice.runners;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.slice.apphooks.SlackListener;
import com.slice.driverutils.GlobalParams;
import com.slice.driverutils.PropertyManager;
import com.slice.driverutils.ServerManager;
import com.slice.utility.AdbUtility;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Properties;

public class RunnerBase {
    private TestNGCucumberRunner testNGCucumberRunner;

    public RunnerBase() {
    }

    @BeforeClass(
            alwaysRun = true
    )
    public void setUpClass() {
        this.testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(
            groups = {"cucumber"},
            description = "Runs Cucumber Scenarios",
            dataProvider = "scenarios"
    )
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        this.testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return this.testNGCucumberRunner == null ? new Object[0][0] : this.testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(
            alwaysRun = true
    )
    public void tearDownClass() {
        if (this.testNGCucumberRunner != null) {
            this.testNGCucumberRunner.finish();
        }
    }
    @AfterSuite(alwaysRun = true)
    public void quit() throws IOException {
        SlackListener.postMessage();
        ServerManager serverManager = new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();
        }
//        AdbUtility adbUtility = new AdbUtility(UUID.randomUUID().toString());
//        adbUtility.killSystemPort();
    }

    public static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    public static void allowPermissions() throws IOException {
        GlobalParams globalParams = new GlobalParams();
        AdbUtility adb = new AdbUtility(globalParams.getUDID());
        adb.killSystemPort();
        Properties props = new PropertyManager().getProps();
        String packageName = props.getProperty("androidAppPackage");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.CAMERA");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.RECORD_AUDIO");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.RECEIVE_SMS");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.ACCESS_FINE_LOCATION");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.WRITE_EXTERNAL_STORAGE");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.READ_EXTERNAL_STORAGE");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.ACCESS_COARSE_LOCATION");
        adb.adbExecute("shell pm grant " + packageName + " android.permission.POST_NOTIFICATIONS");
    }
}
