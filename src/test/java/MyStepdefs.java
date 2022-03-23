import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class MyStepdefs {
    private WebDriver driver;

    @Given("I use {string} as browser")
    public void createBrowser(String browser) {
        DriveCreator newBrowser = new DriveCreator();
        driver = newBrowser.createBrowser(browser);
        driver.get("https://login.mailchimp.com/signup/");
    }

    @Given("I write in {string} in email")
    public void iWriteInInEmail(String email) {

        //Reject all cookies, later when it might have appeared
        rejectCookies(driver, By.id("onetrust-reject-all-handler"));

        if (email.equals("gherkinTester")){
            String currentEmail = addDateToVariable(email);
            currentEmail += "@gmail.com";
            inputText(driver, By.id("email"), currentEmail);
        } else {
            inputText(driver, By.id("email"), email);
        }

    }

    @Given("I write in {string} in username")
    public void writeUsername(String userName) {
        //If userName is tooLongUserName it will enter a for-loop to create an even longer name
        //If userName is gherkinTest it will add the current time and date to the gherkinTest name
        //Otherwise just put in the given userName

        if (userName.equals("gherkinTest")) {
            String currentUserName = addDateToVariable(userName);
            inputText(driver, By.id("new_username"), currentUserName);
        } else if (userName.equals("tooLongUserName")) {
            for (int i = 0; i < 7; i++) {
                inputText(driver, By.id("new_username"), userName);
            }
        } else {
            inputText(driver, By.id("new_username"), userName);
        }
    }

    @Given("I write in {string} in password")
    public void writePassword(String password) {
        inputText(driver, By.id("new_password"), password);

        //Scroll page to bottom of the page
        scrollPage(driver);
    }

    @When("I click the sign up button")
    public void clickSignUp() {
        clickButton(driver, By.id("create-account"));
    }

    @Then("A message saying {string} appears")
    public void tryToCreateUser(String message) {
        //Message will be a shorted down version of the correct thing that will happen. Success will be a successful new user
        //noMail will be the error message with no email etc.
        String expected, actual;

        switch (message.toLowerCase(Locale.ROOT)) {
            case "success":
                expected = "Success | Mailchimp";
                actual = driver.getTitle();
                assertEquals(expected, actual);
                driver.quit();
                break;
            case "noemail":
                expected = "Please enter a value";
                actual = getText(driver, By.className("invalid-error"));
                assertEquals(expected, actual);
                driver.quit();
                break;
            case "longuser":
                expected = "Enter a value less than 100 characters long";
                actual = getText(driver, By.className("invalid-error"));
                assertEquals(expected, actual);
                driver.quit();
                break;
            case "blockuser":
                expected = "Another user with this username already exists. Maybe it's your evil twin. Spooky.";
                actual = getText(driver, By.className("invalid-error"));
                assertEquals(expected, actual);
                driver.quit();
                break;
            default:
                System.out.println("Something weird must have happened, most likely too weak password. Please check your inputs again.");
        }

    }
/* Old @Then which was different from when a correct user was created

    @Then("An error message saying {string} appears")
    public void anErrorMessage(String errorMessage) {
        //errorMessage will be used as "expected"


        String actual = getText(driver, By.className("invalid-error"));
        assertEquals(errorMessage, actual);
        driver.quit();
    }

 */


    //Several methods to use

    //Scroll the page by 300 pixels, it will be enough to get to the bottom of the screen or at least to see the signup button
    private static void scrollPage(WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,300)");
    }

    private static void inputText(WebDriver driver, By by, String text) {
        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated
                (by));
        driver.findElement(by).sendKeys(text);
    }

    private static void clickButton(WebDriver driver, By by) {

        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(by));
        driver.findElement(by).click();
    }

    //Had to create this for itself to wait for cookies and the clickbutton method is changed to elementLocated instead
    //otherwise it didn't really work for me
    private static void rejectCookies(WebDriver driver, By by) {

        (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    private String getText(WebDriver driver, By by) {
        WebDriverWait foobar = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = foobar.until(ExpectedConditions.presenceOfElementLocated(by));
        String text = element.getText();
        return text;
    }

    //Method to add year, month, day, hour, minute and seconds to a variable in order
    //to eliminate manual editing of the scenarios
    private String addDateToVariable(String variableToChange) {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyyMMdd" + "HHmmss");
        LocalDateTime currentTime = LocalDateTime.now();
        String timeRightNow = timeFormat.format(currentTime);

        variableToChange += timeRightNow;
        return variableToChange;
    }


}
