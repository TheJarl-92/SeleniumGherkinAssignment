import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Locale;

public class DriveCreator {
    public WebDriver createBrowser(String browser) {
        if (browser.toLowerCase(Locale.ROOT).equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:/Selenium/chromedriver.exe");
            return new ChromeDriver();
        } else if(browser.toLowerCase(Locale.ROOT).equals("edge")){
            System.setProperty("webdriver.edge.driver", "C:/Selenium/msedgedriver.exe");
            return new EdgeDriver();
        } else {
            //Force a standard browser if the input is wrong
            System.setProperty("webdriver.chrome.driver", "C:/Selenium/chromedriver.exe");
            return new ChromeDriver();
        }

    }
}
