package com.slice.driverutils;

import com.slice.helper.ParentExtender;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Purushotham
 */
public class ServerManager extends ParentExtender {
    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();
    BaseUtil utils = new BaseUtil();

    public ServerManager() throws IOException {
    }

    public AppiumDriverLocalService getServer() {
        return server.get();
    }

  public AppiumDriverLocalService startServer(String OS)
  {
    utils.log().info("starting appium server");
    if (OS.equalsIgnoreCase("MAC"))
    {
      AppiumDriverLocalService server = MacGetAppiumService();
      if(!server.isRunning())
          server.start();

      if (server == null || !server.isRunning())
      {
        utils.log().fatal("Appium server not started. ABORT!!!");
        throw new AppiumServerHasNotBeenStartedLocallyException(
                "Appium server not started. ABORT!!!");
      }
      server.clearOutPutStreams();
      this.server.set(server);
      utils.log().info("Appium server started");
    }
    return server.get();
  }

    public AppiumDriverLocalService MacGetAppiumService() {
        GlobalParams params = new GlobalParams();
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(new File(params.getPlatformName() + "_"
                        + params.getDeviceName() + File.separator + "Server.log")));

    }
}
