package com.slice.apphooks;

import com.slice.driverutils.*;
import com.slice.enums.Enviromnent;
import com.slice.helper.ParentExtender;
import com.slice.runners.RunnerBase;
import com.slice.utility.MapperUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

import java.io.IOException;

import static com.slice.runners.RunnerBase.getDefaultObjectMapper;

/**
 * Created by Purushotham
 */
public class Hooks {

    @Before
    public void initialize() throws Exception {
        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();
        //new ServerManager().startServer("MAC");
        RunnerBase.allowPermissions();
        MapperUtils.init(getDefaultObjectMapper());
        new DriverManager().initializeDriver();
        new VideoManager()
                .startRecording(
                        Boolean.parseBoolean(new PropertyManager().getProps().getProperty(("screenRecorder"))));

    }

    @After
    public void quit(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            byte[] screenshot = new DriverManager().getDriver().getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
        new VideoManager()
                .stopRecording(
                        scenario.getName(),
                        Boolean.parseBoolean(new PropertyManager().getProps().getProperty(("screenRecorder"))));
        DriverManager driverManager = new DriverManager();
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
    }

}
