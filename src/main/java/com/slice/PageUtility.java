package com.slice;

import com.slice.driverutils.BaseUtil;
import com.slice.driverutils.DriverManager;
import com.slice.driverutils.GlobalParams;
import com.slice.enums.Locators;
import com.slice.utility.RandomGenerator;
import io.appium.java_client.*;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;


public class PageUtility extends RandomGenerator {
    public static AppiumDriver getCurrentDriver() {
        DriverManager driverManager = new DriverManager();
        return driverManager.getDriver();
    }

    private AppiumDriver driver = PageUtility.getCurrentDriver();
    public BaseUtil utils = new BaseUtil();

    public static final long WAIT = 10;

    public Logger log() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
    }

    public void waitForVisibility(MobileElement e) {
        WebDriverWait wait = new WebDriverWait(driver, BaseUtil.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }

    public void waitForVisibility(By e) {
        WebDriverWait wait = new WebDriverWait(driver, BaseUtil.WAIT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public boolean isElementDisplayed(MobileElement e){
        waitForVisibility(e);
        return e.isDisplayed();
    }

    public void clear(MobileElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(MobileElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void click(MobileElement e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.click();
    }

    public void doubleClick(MobileElement element, String msg) {
        element.click();
        try {
            element.click();
        } catch (Exception e) {

        }

        utils.log().info(msg);
    }

    public void click(By e, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        driver.findElement(e).click();
    }

    public void sendKeys(MobileElement e, String txt, String msg) {
        waitForVisibility(e);
        utils.log().info(msg);
        e.sendKeys(txt);
    }

    public void sendKeysWithoutWait(MobileElement e, String txt, String msg) {
        e.sendKeys(txt, "Entered text :" + txt);
    }

    public String getAttribute(MobileElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getAttribute(By e, String attribute) {
        waitForVisibility(e);
        return driver.findElement(e).getAttribute(attribute);
    }

    public String getText(MobileElement e) {
        String txt;
        switch (new GlobalParams().getPlatformName()) {
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException(
                        "Unexpected value: " + new GlobalParams().getPlatformName());
        }
        return txt;
    }

    public String getText(By e, String msg) {
        String txt;
        switch (new GlobalParams().getPlatformName()) {
            case "Android":
                txt = getAttribute(e, "text");
                break;
            case "iOS":
                txt = getAttribute(e, "label");
                break;
            default:
                throw new IllegalStateException(
                        "Unexpected value: " + new GlobalParams().getPlatformName());
        }
        utils.log().info(msg + txt);
        return txt;
    }

    public void closeApp() {
        ((InteractsWithApps) driver).closeApp();
    }

    public void launchApp() {
        ((InteractsWithApps) driver).launchApp();
    }

    public MobileElement andScrollToElementUsingUiScrollable(
            String childLocAttr, String childLocValue) {
        return (MobileElement)
                ((FindsByAndroidUIAutomator) driver)
                        .findElementByAndroidUIAutomator(
                                "new UiScrollable(new UiSelector()"
                                        + ".scrollable(true)).scrollIntoView("
                                        + "new UiSelector()."
                                        + childLocAttr
                                        + "(\""
                                        + childLocValue
                                        + "\"));");
    }

    public void swipe(int startX, int startY, int endX, int endY, int millis)
            throws InterruptedException {
        TouchAction t = new TouchAction(driver);
        t.press(point(startX, startY))
                .waitAction(waitOptions(ofMillis(millis)))
                .moveTo(point(endX, endY))
                .release()
                .perform();
    }

    public void tapUsingCoOrdinates(int x, int y){
        TouchAction t = new TouchAction(driver);
        t.tap(point(x,y)).release().perform();
    }

    public void tapAtCentreOfScreen(){
        TouchAction t = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int startx = (int)(size.getWidth() * 0.5);
        int starty = (int) (size.getHeight() * 0.5);
        log().info("tapping at centre of screen");
        t.tap(point(startx,starty)).release().perform();
    }

    public void waitForMobileElement(MobileElement mobileElement, int seconds) {
        try {
            new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOf(mobileElement));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitImplicitly(){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void waitForElement(By locator, int seconds) {
        try {
            new WebDriverWait(driver, seconds)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.err.println("Waited " + seconds + " seconds for " + locator);
            e.printStackTrace();
        }
    }

    public void pressBackKey() {
        driver.slicegate().back();
    }

    public void waitForSeconds(int seconds) {
        int milliSeconds = seconds * 1000;
        try {
            Thread.sleep(milliSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public MobileElement searchElementInListAndroid(Locators locator, String value, int index) {
        Reporter.log("Searching element in Android: " + value + " index :" + index, true);
        By by = null;
        MobileElement object = null;
        try {
            switch (locator) {
                case TEXT:
                    by = MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setMaxSearchSwipes(5000).scrollIntoView(new UiSelector().text(\"" + value + "\"));");
                    break;

                case ACCESSIBILITY_ID:
                    by = MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setMaxSearchSwipes(5000).scrollIntoView(new UiSelector().description(\"" + value + "\"));");
                    break;

                case CLASSNAME:
                    by = MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setMaxSearchSwipes(5000).scrollIntoView(new UiSelector().className(\"" + value + "\").index("
                            + index + "));");
                    break;

                case RESOURCE_ID:
                    by = MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).setMaxSearchSwipes(5000).scrollIntoView(new UiSelector().resourceId(\"" + value + "\"));");
                    break;

                default:
                    Reporter.log(locator + " is not a valid android locator to search ", true);
            }
            object = (MobileElement) driver.findElement(by);
        } catch (Exception e) {
            Reporter.log("Object not found in list", true);
        }
        return object;
    }

    public MobileElement getXpathFromText(String text) {
        return (MobileElement) driver.findElement(By.xpath(String.format("//android.widget.TextView[@text='%s']", text)));
    }

    public MobileElement getChildElementFromParent(MobileElement parent, String childPath) {
        By by = By.xpath(childPath);
        return parent.findElement(by);
    }

    public void checkForElement(List<MobileElement> mobileElement) throws InterruptedException {
        int i = 0;
        boolean value = false;
        while (i <= 7 && !value) {
            Thread.sleep(3000);
            if (mobileElement.size() > 0) {
                value = true;
            }
            log().info(i * 3000 + " : This much second gone but still page is not loaded properly ");
            i++;
        }
    }

    public void checkForVisibleText(String text) throws InterruptedException {
        driver.setSetting(Setting.WAIT_FOR_IDLE_TIMEOUT, 0);
        int i = 0;
        while (i <= 1000) {
            if (driver.getPageSource().contains(text)) {
                log().info("page is loaded properly");
                break;
            } else {
                Thread.sleep(100);
                if(i%50==0){
                    log().info(i * 100 + " : This much millisecond gone but still page is not loaded properly for text : "+text);
                }
                i++;
            }
        }
    }

    public void checkForVisibleTextWithCounter(String text,int userIterationCounter) throws InterruptedException {
        int i = 0;
        while (i <= userIterationCounter) {
            if (driver.getPageSource().contains(text)) {
                log().info("page is loaded properly");
                break;
            } else {
                Thread.sleep(3000);
                log().info(i * 3000 + " : This much millisecond gone but still page is not loaded properly for text  "+text);
                i++;
            }
            log().info("userIterationCounter is : ",userIterationCounter);
        }
    }

    public void checkForElementWithText(List<MobileElement> mobileElement, String expectedText) throws InterruptedException {
        int i = 0;
        boolean value = false;
        while (i <= 7 && !value) {
            Thread.sleep(3000);
            if (mobileElement.size() > 0 && mobileElement.get(0).getText().equals(expectedText)) {
                value = true;
            }
            log().info(i * 3000 + " : This much second gone but still page is not loaded properly ");
            i++;
        }
    }

    public MobileElement getCorrectElement(List<MobileElement> mobileElements) {
        return mobileElements.stream().filter(element -> element.isDisplayed()).findFirst().get();
    }

    public void scrollDown(){
        PageInitializer pageInitializer = new PageInitializer();
        int height = pageInitializer.offerDetailsContainer.getSize().getHeight();
        TouchAction action = new TouchAction(driver);
        action.press(new PointOption().withCoordinates(500, height/2)).moveTo(new PointOption().withCoordinates(500, 0)).release().perform();
        System.out.println("Kritik");
    }

    public void tapByElement (MobileElement androidElement) {
        waitForSeconds(CommonConstants.WAIT_FOR_1_SECOND);
        new TouchAction(driver)
                .tap(tapOptions().withElement(element(androidElement)))
                .waitAction(waitOptions(ofMillis(250))).perform();
    }
    //Tap by coordinates
    public void tapByCoordinates (int x,  int y) {
        new TouchAction(driver)
                .tap(point(x,y))
                .waitAction(waitOptions(ofMillis(250))).perform();
    }
    //Press by element
    public void pressByElement (AndroidElement element, long seconds) {
        new TouchAction(driver)
                .press(element(element))
                .waitAction(waitOptions(ofSeconds(seconds)))
                .release()
                .perform();
    }
    //Press by coordinates
    public void pressByCoordinates (int x, int y, long seconds) {
        new TouchAction(driver)
                .press(point(x,y))
                .waitAction(waitOptions(ofSeconds(seconds)))
                .release()
                .perform();
    }
    //Horizontal Swipe by percentages
    public void horizontalSwipeByPercentage (double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.height * anchorPercentage);
        int startPoint = (int) (size.width * startPercentage);
        int endPoint = (int) (size.width * endPercentage);
        new TouchAction(driver)
                .press(point(startPoint, anchor))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endPoint, anchor))
                .release().perform();
    }
    //Vertical Swipe by percentages
    public void verticalSwipeByPercentages(double startPercentage, double endPercentage, double anchorPercentage) {
        Dimension size = driver.manage().window().getSize();
        int anchor = (int) (size.width * anchorPercentage);
        int startPoint = (int) (size.height * startPercentage);
        int endPoint = (int) (size.height * endPercentage);
        new TouchAction(driver)
                .press(point(anchor, startPoint))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(anchor, endPoint))
                .release().perform();
    }
    //Swipe by elements
    public void swipeByElements (AndroidElement startElement, AndroidElement endElement) {
        int startX = startElement.getLocation().getX() + (startElement.getSize().getWidth() / 2);
        int startY = startElement.getLocation().getY() + (startElement.getSize().getHeight() / 2);
        int endX = endElement.getLocation().getX() + (endElement.getSize().getWidth() / 2);
        int endY = endElement.getLocation().getY() + (endElement.getSize().getHeight() / 2);
        new TouchAction(driver)
                .press(point(startX,startY))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endX, endY))
                .release().perform();
    }
    //Multitouch action by using an android element
    public void multiTouchByElement (AndroidElement androidElement) {
        TouchAction press = new TouchAction(driver)
                .press(element(androidElement))
                .waitAction(waitOptions(ofSeconds(1)))
                .release();
        new MultiTouchAction(driver)
                .add(press)
                .perform();
    }

    public void allowPermissions(AppiumDriver driver){
        if(isAlertPresent()) {
            driver.switchTo().alert().accept();
        }
    }

    public boolean isAlertPresent() {
        try {
            getCurrentDriver().switchTo().alert();
            utils.log().info("ALERT IS PRESENT !! ");
            return true;
        } catch (Exception e) {
            utils.log().info("ALERT IS NOT PRESENT !! ");
            return false;
        }
    }

}

