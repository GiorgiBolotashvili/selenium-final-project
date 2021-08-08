import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalProject {
    private WebDriver driver;
    private String test;
    private String email;

    @BeforeTest
    @Parameters("browser")
    public void start(String browser) throws Exception{

        if(browser.equalsIgnoreCase("Chrome"))
        {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("run test in chrome: ");
            test = "chrome";
            email = CreateRandomEmail();
        }
        else if(browser.equalsIgnoreCase("Edge"))
        {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            System.out.println("run test in edge: ");
            test = "edge";
            email = CreateRandomEmail();
        }
        else
            throw new Exception("Browser is not correct");

        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.OFF);
    }

    @Test
    public void finalTest() throws InterruptedException {
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
//        FluentWait fluentWait = new FluentWait(driver);
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a")).click();

        String firstName = "firstName";
        String lastName = "lastName";
//        String email = "123456789aaa@yahoo.com";
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

        driver.findElement(By.cssSelector("input[type='checkbox']")).click();
        driver.findElement(By.className("btn-primary")).click();
//        driver.findElement(By.cssSelector("input[type='submit']"));

     //   Thread.sleep(10000);
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
//        Write a review and submit
        driver.findElement(By.cssSelector("input#input-name")).sendKeys("Giorgi");
        String myReview = "The Apple iPod Classic offers a solid, understated design with an easy-to-use " +
                        "interface and unbelievably generous capacity.";
        driver.findElement(By.cssSelector("textarea#input-review")).sendKeys(myReview);
        driver.findElement(By.xpath("//*[@id=\"form-review\"]/div[4]/div/input[5]")).click();
        driver.findElement(By.id("button-review")).click();
        Thread.sleep(10000);
//        save iPod classic price
        String price = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/ul[2]/li[1]/h2")).getText();
        driver.findElement(By.id("button-cart")).click();
//        Wait for the product added to the cart to appear. Check by price.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'"+price+"')]")));
        System.out.println(test + ": " + driver.findElement(By.id("cart-total")).getText());
        Assert.assertTrue(driver.findElement(By.id("cart-total")).getText().contains(price));

        driver.findElement(By.xpath("//*[@id=\"cart\"]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"cart\"]/ul/li[2]/div/p/a[2]/strong")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='firstname']")));
        driver.findElement(By.cssSelector("input[id$='firstname']")).sendKeys(firstName);
        driver.findElement(By.cssSelector("input[id$='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[id$='address-1']")).sendKeys("my address");
        driver.findElement(By.cssSelector("input[id$='city']")).sendKeys("city");
        driver.findElement(By.cssSelector("input[id$='postcode']")).sendKeys("1010");

        List<WebElement> countryes = driver.findElements(By.xpath("//*[@id=\"input-payment-country\"]/option"));
        for ( WebElement c: countryes) {
            if(c.getText().equals("Georgia"))
                c.click();
        }

        List<WebElement> states = driver.findElements(By.xpath("//*[@id=\"input-payment-zone\"]/option"));
        for ( WebElement s: states) {
            if(s.getText().equals("Tbilisi"))
                s.click();
        }

        driver.findElement(By.xpath("//*[@id=\"button-payment-address\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-shipping-address\"]")));
        driver.findElement(By.xpath("//*[@id=\"button-shipping-address\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-shipping-method\"]")));
        driver.findElement(By.xpath("//*[@id=\"button-shipping-method\"]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"collapse-payment-method\"]/div/div[2]/div/input[1]")));
        driver.findElement(By.xpath("//*[@id=\"collapse-payment-method\"]/div/div[2]/div/input[1]")).click();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-payment-method\"]")));
        driver.findElement(By.xpath("//*[@id=\"button-payment-method\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-confirm\"]")));
        WebElement table = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        String subTotal;
        String shipingRate;
        String total;
        System.out.println(test + ": Check subtotal, flat Shipping Rate and total amount is correct");
        for (int r=1; r<=rows.size(); r++){
                WebElement item = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr["+r+"]/td[1]"));
                if(item.getText().equals("Sub-Total:")){
                    subTotal =  driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr["+r+"]/td[2]")).getText();
                    System.out.println(item.getText()+": "+subTotal);
                }
                else if (item.getText().equals("Flat Shipping Rate:")){
                    shipingRate =  driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr["+r+"]/td[2]")).getText();
                    System.out.println(item.getText()+": "+shipingRate);
                }
                else if(item.getText().equals("Total:")){
                    total =  driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr["+r+"]/td[2]")).getText();
                    System.out.println(item.getText()+": "+total);
                }
        }
        driver.findElement(By.xpath("//*[@id=\"button-confirm\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/p[2]/a[2]")));
        driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/a[2]")).click();
        WebElement history = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table"));
        List<WebElement> historyRows = history.findElements(By.tagName("tr"));

        int columnSize = historyRows.get(0).findElements(By.tagName("td")).size();
        for (int c=1; c<=columnSize; c++){
            WebElement item = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/thead/tr/td["+c+"]"));
            if (item.getText().equals("Status")){
                String pending = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td["+c+"]")).getText();
                Assert.assertEquals(pending,"Pending");
                System.out.println("1");
            }
            else if (item.getText().contains("Date")){
                String date = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td["+c+"]")).getText();
                Assert.assertEquals(date,new Date());
                System.out.println("2");
            }
        }
      System.out.println(test + ": finish " + new Date());


    }
    @AfterMethod
    public void tearDown(){
//        driver.quit();
    }

    private String CreateRandomEmail() {
        Random r = new Random();
        String email = "";
        for (int i=0; i<8; i++)
        {
            email += (char) ('a' + r.nextInt(26));
        }
        System.out.println(email);
        return email + "@yahoo.com";
    }
}
