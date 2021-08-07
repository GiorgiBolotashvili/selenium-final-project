import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class FinalProject {
    private WebDriver driver;

    @BeforeMethod
    public void start(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @Test
    public void finalTest() throws InterruptedException {
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        FluentWait fluentWait = new FluentWait(driver);
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a")).click();

        String firstName = "Giorgi";
        String lastName = "Bolotashvili";
        String email = "gio_bolota@yahoo.com";
        String telephone = "+995 558 99 99 99";
        String password = "Password123";
        driver.findElement(By.cssSelector("input#input-firstname")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input#input-lastname")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input#input-email")).sendKeys(email);
        driver.findElement(By.cssSelector("input#input-telephone")).sendKeys(telephone);
        driver.findElement(By.cssSelector("input#input-password")).sendKeys(password);
        driver.findElement(By.cssSelector("input#input-confirm")).sendKeys(password);

        WebElement subscribe = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset[3]/div/div/label[1]"));
        if ((!subscribe.isSelected()) & subscribe.getText().equals("Yes"))
            subscribe.click();
//        driver.findElement(By.xpath("//*[@id=\"content\"]/form/div/div/input[1]")).click();
        driver.findElement(By.className("btn-primary")).click();

        WebElement desktop = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/a"));
        action.moveToElement(desktop).build().perform();

        try {
            WebElement allDesktos = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/div/a"));
            if (allDesktos.isDisplayed())
                allDesktos.click();
        }
        catch (NoSuchElementException ex)
        {
            System.out.println(ex.getMessage());
        }
        driver.findElement(By.xpath("//*[@id=\"column-left\"]/div[1]/a[10]")).click();
        WebElement ipod = driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div[1]/div/div[1]/a/img"));
        action.moveToElement(ipod).build().perform();
        Assert.assertEquals(ipod.getAttribute("title"), "iPod Classic");
        if (ipod.getAttribute("title").equals("iPod Classic"))
        {
            driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div[1]/div/div[2]/div[1]/h4/a")).click();
        }
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul[1]/li[1]/a/img")).click();
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("mfp-img")));
        WebElement next = driver.findElement(By.xpath("/html/body/div[2]/div/button[2]"));
//        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[2]/div/div[1]/div/figure/img")));
        while (!driver.findElement(By.className("mfp-counter")).getText().equals("4 of 4")){
            next.click();
        }
        driver.findElement(By.className("mfp-close")).click();
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul[2]/li[2]/a")).click();
        driver.findElement(By.cssSelector("input#input-name")).sendKeys("my Name");
        driver.findElement(By.cssSelector("textarea#input-review")).sendKeys("my text");
        driver.findElement(By.id("button-review")).click();
        driver.findElement(By.id("button-cart")).click();


/*        fluentWait.withTimeout(5,TimeUnit.SECONDS);
        fluentWait.pollingEvery(500,TimeUnit.MILLISECONDS);*/
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'$122')]"))); //ფასი იცვლება
        System.out.println(driver.findElement(By.id("cart-total")).getText());
        System.out.println(driver.findElement(By.id("cart-total")).getText());


      System.out.println("1: finish");


//        Assert.assertTrue(allDesktos.isDisplayed());*/

    }
    @AfterMethod
    public void tearDown(){
//        driver.quit();
    }
}
