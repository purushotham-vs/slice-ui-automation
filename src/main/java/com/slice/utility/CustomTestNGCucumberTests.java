package com.slice.utility;

import com.slice.driverutils.DriverManager;
import com.slice.driverutils.GlobalParams;
import com.slice.driverutils.ServerManager;
import io.cucumber.testng.TestNGCucumberRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import java.io.IOException;
public class CustomTestNGCucumberTests {
    private TestNGCucumberRunner testNGCucumberRunner;

    public CustomTestNGCucumberTests() {

    }

    @BeforeClass(
            alwaysRun = true
    )
    public void setUpClass() throws Exception {
        GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();
       // new ServerManager().startServer("MAC");
        new DriverManager().initializeDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws IOException {
        if (this.testNGCucumberRunner != null) {
            this.testNGCucumberRunner.finish();
        }
        DriverManager driverManager = new DriverManager();
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();
        }
    }
}
