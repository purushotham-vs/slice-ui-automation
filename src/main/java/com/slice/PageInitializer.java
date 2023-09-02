package com.slice;

import com.slice.driverutils.DriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PageInitializer extends PageUtility {
    public AppiumDriver driver;

    @AndroidFindBy(id = "com.sliceapp.dev:id/borrow_icon")
    public MobileElement backButton;

    public PageInitializer() {
        this.driver = new DriverManager().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

}
