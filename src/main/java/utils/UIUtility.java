package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UIUtility {
    public WebDriver driver;
    public WebDriverWait wait;
    public  Actions actions;
    public UIUtility(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
        this.actions = new Actions(driver);
    }

    // Wait until element is clickable
    public void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    // Wait until element is visible
    public void waitAndSendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).sendKeys(text);
    }

    // Wait until element is visible and return text
    public String waitAndGetText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText();
    }

    // Wait until element is invisible
    public void waitForInvisibility(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    // Wait until URL contains a string
    public void waitForUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    // Wait until page title contains text
    public void waitForTitleContains(String titlePart) {
        wait.until(ExpectedConditions.titleContains(titlePart));
    }
    // ===== Highlight element dynamically =====
    private void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String originalStyle = element.getAttribute("style");
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                element, "border: 2px solid red; background: yellow;");
        try { Thread.sleep(200); } catch (InterruptedException e) { }
        js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
                element, originalStyle);
    }

    // ===== CLICK =====
    public void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        highlightElement(element);
        element.click();
        System.out.println("Clicked on element: " + element);
    }

    // ===== SEND KEYS =====
    public void sendKeys(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        highlightElement(element);
        element.clear();
        element.sendKeys(text);
        System.out.println("Sent keys '" + text + "' to element: " + element);
    }

    // ===== GET TEXT =====
    public String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        highlightElement(element);
        String text = element.getText();
        System.out.println("Text from element: " + text);
        return text;
    }

    // ===== IS DISPLAYED =====
    public boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            highlightElement(element);
            boolean displayed = element.isDisplayed();
            System.out.println("Element displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            System.out.println("Element not displayed: " + element);
            return false;
        }
    }

    // ===== HOVER =====
    public void hover(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        highlightElement(element);
        actions.moveToElement(element).perform();
        System.out.println("Hovered over element: " + element);
    }

    // ===== SCROLL INTO VIEW =====
    public void scrollIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        highlightElement(element);
        System.out.println("Scrolled into view: " + element);
    }

    // ===== PRESS ENTER =====
    public void pressEnter(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        highlightElement(element);
        element.sendKeys(Keys.ENTER);
        System.out.println("Pressed ENTER on element: " + element);
    }

    // ===== DOUBLE CLICK =====
    public void doubleClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        highlightElement(element);
        actions.doubleClick(element).perform();
        System.out.println("Double clicked on element: " + element);
    }

    // ===== RIGHT CLICK =====
    public void rightClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        highlightElement(element);
        actions.contextClick(element).perform();
        System.out.println("Right clicked on element: " + element);
    }

    // ===== CLEAR FIELD =====
    public void clearField(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        highlightElement(element);
        element.clear();
        System.out.println("Cleared field: " + element);
    }
}
