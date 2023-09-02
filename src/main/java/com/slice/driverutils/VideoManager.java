package com.slice.driverutils;

import io.appium.java_client.screenrecording.CanRecordScreen;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Purushotham
 */
public class VideoManager
{
  BaseUtil utils = new BaseUtil();

  public void startRecording(Boolean flag)
  {
    if (flag)
    {
      ((CanRecordScreen) new DriverManager().getDriver()).startRecordingScreen();
    }
  }

  public void stopRecording(String scenarioName, Boolean flag) throws IOException
  {
    if (flag)
    {
      GlobalParams params = new GlobalParams();
      String media = ((CanRecordScreen) new DriverManager().getDriver()).stopRecordingScreen();
      String dirPath =
              params.getPlatformName() + "_" + params.getDeviceName() + File.separator + "Videos";

      File videoDir = new File(dirPath);

      synchronized (videoDir)
      {
        if (!videoDir.exists())
        {
          videoDir.mkdirs();
        }
      }
      FileOutputStream stream = null;
      try
      {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss").format(new Date());
        stream = new FileOutputStream(videoDir + File.separator + scenarioName + timestamp + ".mp4");
        stream.write(Base64.decodeBase64(media));
        stream.close();
        utils.log().info("video path: " + videoDir + File.separator + scenarioName + timestamp + ".mp4");
      } catch (Exception e)
      {
        utils.log().error("error during video capture" + e.toString());
      } finally
      {
        if (stream != null)
        {
          stream.close();
        }
      }
    }
  }
}
