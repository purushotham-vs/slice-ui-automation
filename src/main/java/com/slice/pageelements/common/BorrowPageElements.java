package com.slice.pageelements.common;

import com.slice.PageInitializer;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class BorrowPageElements extends PageInitializer {

    @AndroidFindBy(xpath = "//*[@text='borrow / icon']")
    private MobileElement Borrow;

    public void clickOnLoginOrSignUp(){
        click(Borrow,"Clicking on Borrow icon");
    }
}
