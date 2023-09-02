package com.slice.runners;

import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

/**
 * slice-ui-automation
 *
 * @author purushotham
 */

@CucumberOptions(
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "timeline:test-output-thread/"
        },
        features = {"src/test/resources/features/miscellaneouscases"}
        , glue = {"com.slice.stepdef", "com.slice.apphooks"}
        , monochrome = true
        , publish = true
        ,tags = "@e2e"
)
public class Miscellaneousrunner extends RunnerBase {
    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
