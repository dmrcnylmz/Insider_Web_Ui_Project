package step;

import Base.BaseMethods;
import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;

public class StepImplementation extends BaseMethods {



    @Step("<second> second wait")
    public void waitBySeconds(int seconds){
        waitByMilliSeconds(seconds * 1000);
    }


    @Step("Go to <url>")
    public void goToUrl(String url){

        driver.get(url);
        logger.info(url + " going to.");

    }

    @Step("Wait for <key> and click")
    public void checkElementVisibiltyAndClick(String key){
        isElementVisible(key,5);
        isElementClickable(key,10);
        clickElement(key);
    }
 @Step("If element <key> visible then click")
    public void clickElementIfElementVisible(String key){
      if( isElementVisible(key,5)){

        clickElement(key);
    }}

    @Step("Hover to <key>")
    public void hoverStep(String key){
          isElementVisible(key,5);
          hoverElement(key);
    }
    @Step("Is element <key> on the page ? If not on the page write <mesaj>")
    public void getButtonControl(String key, String msg)  {
        isElementVisible(key,5);
        Assert.assertTrue(msg, findElementByKey(key).isDisplayed());

    }

    @Step("Is <key> element Visible ? <timeout>")
    public boolean isElementVisible(String key, int timeout){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        try{
            WebDriverWait wait = new WebDriverWait(driver,timeout);
           waitVisibilityOfElementLocatedBy(ElementHelper.getElementInfoToBy(elementInfo));
            return true;
        }catch (Exception e){
            logger.info(key +" not visible");
            return false;
        }
    }
    @Step("Is <key> element Clickable ? <timeout>")
    public boolean isElementClickable(String key, int timeout){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        try{
            WebDriverWait wait = new WebDriverWait(driver,timeout);
            waitClickableOfElementLocatedBy(ElementHelper.getElementInfoToBy(elementInfo));
            return true;
        }catch (Exception e){
            logger.info(key +" not visible");
            return false;
        }
    }

    @Step("Write <text> to the <key> and clear area")
    public void sendKeys(String text, String key){

        clearAndSendKey(text,key);
        logger.info(text+" written to "+key);
    }


    @Step("Logger -> <text>")
    public void loggerInfo (String text){

        logger.info(text);

    }



    @Step("Javascript ile tıkla <key>")
    public void javaScriptClicker(String key){
        isElementVisible(key,5);
        isElementClickable(key,5);
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement element = driver.findElement(ElementHelper.getElementInfoToBy(elementInfo));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", element);
    }
  @Step("Javascript ile elemente kaydır <key>")
    public void javaScriptScroller(String key){

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        WebElement element = driver.findElement(ElementHelper.getElementInfoToBy(elementInfo));
      ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    @Step("Gauge rapora <text> mesajını yaz ve ekran görüntüsü al")
    public void takeScreenShot(String text) {
        Gauge.captureScreenshot();
        Gauge.writeMessage(text);
        logger.info("gauge raporuna bu adımda " + text + " mesajı eklendi");
    }
@Step(("Action to the new tab"))
    public void actionOnNewTab()
{
        switchToNewWindow();
}
}
