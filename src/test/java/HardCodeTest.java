import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HardCodeTest {
    WebDriver driver;

    @BeforeClass
    public void start() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://google.com.ua");
    }


    @Test
    public void testSmth() {
        WebElement searchField = driver.findElement(By.name("q"));
        searchField.sendKeys("Java");
        searchField.sendKeys(Keys.RETURN);
        WebElement imageButton = driver.findElement(By.xpath("(//div[contains(@class, \"hdtb-mitem\")]/a)[1]"));
        imageButton.click();
        List<WebElement> images = driver.findElements(By.xpath("//div[@id=\"islrg\"]//img"));
        Assert.assertEquals(images.get(0).getAttribute("alt"), "Javaa — Википедия", "Wrong value of attribute");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (ITestResult.FAILURE == result.getStatus()) {
            printScreenshot();
            driver.quit();
        } else {
            driver.quit();
        }
    }


    public void printScreenshot() {
        Date dateNow = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = format1.format(dateNow) + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("D:\\Screenshots\\" + fileName));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
