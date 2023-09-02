package com.slice.driverutils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.asserts.SoftAssert;

public class BaseUtil
{
  public static final int WAIT = 10;
  public static SoftAssert softAssert = new SoftAssert();

  public Logger log()
  {
    return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
  }
}

