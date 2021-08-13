import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class FinalProject {
    private WebDriver driver;
    private String test;
    private String email;

    @BeforeTest
    @Parameters("browser")
    public void start(String browser) throws Exception {

        if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("run test in chrome: ");
            test = "Chrome";
            email = CreateRandomEmail();
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            System.out.println("run test in edge: ");
            test = "Edge";
            email = CreateRandomEmail();
        } else
            throw new Exception("Browser is not correct");
    }

    @Test
    public void finalTest() throws IOException {
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        TakesScreenshot screenShot = ((TakesScreenshot) driver);
        String fileName = "screenShot" + test + ".jpg";
        File filePath = new File("D:\\" + fileName);
        File screen = screenShot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screen, filePath);
//        Register new account
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/a/span[1]")).click();                              // Using relative XPath: example one
        driver.findElement(By.xpath("//*[@id=\"top-links\"]/ul/li[2]/ul/li[1]/a")).click();
        String firstName = "firstName";
        String lastName = "lastName";
        String telephone = "+995 558 99 99 99";
        String password = "Password123";
        driver.findElement(By.cssSelector("input#input-firstname")).sendKeys(firstName);                                // Using cssSelector: example one
        driver.findElement(By.cssSelector("input#input-lastname")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input#input-email")).sendKeys(email);
        driver.findElement(By.cssSelector("input#input-telephone")).sendKeys(telephone);
        driver.findElement(By.cssSelector("input#input-password")).sendKeys(password);
        driver.findElement(By.cssSelector("input#input-confirm")).sendKeys(password);
        WebElement subscribe = driver.findElement(By.xpath("//*[@id=\"content\"]/form/fieldset[3]/div/div/label[1]"));
        if ((!subscribe.isSelected()) & subscribe.getText().equals("Yes"))
            subscribe.click();
        driver.findElement(By.cssSelector("input[type='checkbox']")).click();                                           // Using cssSelector: example two
        driver.findElement(By.className("btn-primary")).click();                                                        // Using className: example one
//        go to page: MP3 Players
        WebElement desktop = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/a"));
        action.moveToElement(desktop).build().perform();
        try {
            WebElement allDesktos = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/div/a"));
            if (allDesktos.isDisplayed())
                allDesktos.click();
        } catch (NoSuchElementException ex) {
            System.out.println(test + ": " +ex.getMessage());
        }
        WebElement mp3 = driver.findElement(By.xpath("//*[@id=\"column-left\"]/div[1]/a[10]"));
        js.executeScript("arguments[0].click()", mp3);                                                               // Using JavascriptExecutor: example one
//        check that 'iPod Shuffle' text is visible on mouse hover
        WebElement ipod = driver.findElement(By.xpath("//*[@id=\"content\"]/div[4]/div[3]/div/div[1]/a/img"));
        action.moveToElement(ipod).build().perform();
        Assert.assertEquals(ipod.getAttribute("title"), "iPod Shuffle");
        if (ipod.getAttribute("title").equals("iPod Shuffle")) {
            ipod.click();
        }
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul[1]/li[1]/a/img")).click();
        WebElement next = driver.findElement(By.xpath("//*[contains(@class,'mfp-arrow-right')]"));                      // Using relative XPath: example two
        while (!driver.findElement(By.className("mfp-counter")).getText().equals("5 of 5")) {                           // Using className: example two
            next.click();
        }
        driver.findElement(By.className("mfp-close")).click();                                                          // // Using className: example three
        driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/ul[2]/li[2]/a")).click();
//        Write a review and submit
        driver.findElement(By.cssSelector("input#input-name")).sendKeys("Giorgi");
        String myReview = "The Apple iPod Shuffle is affordably priced with a compact design and long-lasting battery." +
                " It's both stylish and easily wearable, thanks to its sturdy built-in clip.";
        driver.findElement(By.cssSelector("textarea#input-review")).sendKeys(myReview);
        driver.findElement(By.xpath("//*[@id=\"form-review\"]/div[4]/div//following::input[5]")).click();               // Using relative XPath: example three
        driver.findElement(By.id("button-review")).click();   //*[@class="btn-primary""]
//        save iPod Shuffle total price
        WebElement price = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[2]/ul[2]/li[1]/h2"));
        String firstPrice = (String) js.executeScript("return arguments[0].innerText;", price);                      // Using JavascriptExecutor: example two
        System.out.println(test + ": First total amount is: " + firstPrice);
//        save empty cart text
        String emptyCart = driver.findElement(By.cssSelector("#cart-total")).getText();
        driver.findElement(By.id("button-cart")).click();
//        Wait for the product added to the cart to appear. Check by price.
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + firstPrice + "')]")));
//        Check by item's count and price
        String fullCart = driver.findElement(By.id("cart-total")).getText();
        if (emptyCart.equals(fullCart))
        {
            System.out.println(test + ": " + "Product was not successfully added to cart!");
        }
        else {
            System.out.println(test + ": " + "Product was successfully added to cart!");
        }
        Assert.assertTrue(fullCart.contains(firstPrice));
        driver.findElement(By.xpath("//*[@id=\"cart\"]/button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart\"]/ul/li[2]/div/p/a[2]/strong")));
        driver.findElement(By.xpath("//*[@id=\"cart\"]/ul/li[2]/div/p/a[2]/strong")).click();

//        Fill Billing Details
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[id$='firstname']")));
        driver.findElement(By.cssSelector("input[id$='firstname']")).sendKeys(firstName);                               // Using cssSelector: example three
        driver.findElement(By.cssSelector("input[id$='lastname']")).sendKeys(lastName);
        driver.findElement(By.cssSelector("input[id$='address-1']")).sendKeys("my address");
        driver.findElement(By.cssSelector("input[id$='city']")).sendKeys("city");
        driver.findElement(By.cssSelector("input[id$='postcode']")).sendKeys("1010");
//        choose Georgia and Tbilisi
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"input-payment-country\"]/option")));
        List<WebElement> countryes = driver.findElements(By.xpath("//*[@id=\"input-payment-country\"]/option"));
        for (WebElement c : countryes) {
            if (c.getText().equals("Georgia"))
                c.click();
        }
        List<WebElement> states = driver.findElements(By.xpath("//*[@id=\"input-payment-zone\"]/option"));
        for (WebElement s : states) {
            if (s.getText().equals("Tbilisi"))
                s.click();
        }

        driver.findElement(By.xpath("//*[@id=\"button-payment-address\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-shipping-address\"]")));
        js.executeScript("document.getElementById('button-shipping-address').click();");                             // Using JavascriptExecutor: example three

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-shipping-method\"]")));
        js.executeScript("document.getElementById('button-shipping-method').click();");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"collapse-payment-method\"]/div/div[2]/div/input[1]")));
        driver.findElement(By.xpath("//*[@id=\"collapse-payment-method\"]/div/div[2]/div/input[1]")).click();
        js.executeScript("document.getElementById('button-payment-method').click();");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"button-confirm\"]")));
        WebElement table = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        String subTotal = "";
        String shipingRate;
        String total;
//      Check subtotal, flat Shipping Rate and total amount is correct
        for (int r = 1; r <= rows.size(); r++) {
            WebElement item = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[" + r + "]/td[1]"));
            if (item.getText().equals("Sub-Total:")) {
                subTotal = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[" + r + "]/td[2]")).getText();
                System.out.println(test + ": " + item.getText() + "  " + subTotal);
            }
            else if (item.getText().equals("Flat Shipping Rate:")) {
                shipingRate = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[" + r + "]/td[2]")).getText();
                System.out.println(test + ": " + item.getText() + "  " + shipingRate);
            }
            else if (item.getText().equals("Total:")) {
                total = driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tfoot/tr[" + r + "]/td[2]")).getText();
                System.out.println(test + ": " + item.getText() + "  " + total);
            }
        }
//      Compare first total amount with the final total amount
        if (firstPrice.equals(subTotal)) {
            System.out.println(test + ": " +"The firts amount is equal to the final amount");
        } else
            System.out.println(test + ": " +"The firts amount is \u001B[31m NOT EQUALS \u001B[0m to the final amount");
        driver.findElement(By.xpath("//*[@id=\"button-confirm\"]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/p[2]/a[2]")));
        driver.findElement(By.xpath("//*[@id=\"content\"]/p[2]/a[2]")).click();
//        check that status is 'Pending' and date equal to current date
        WebElement history = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table"));
        List<WebElement> historyRows = history.findElements(By.tagName("tr"));
        int columnSize = historyRows.get(0).findElements(By.tagName("td")).size();
        for (int c = 1; c <= columnSize; c++) {
            WebElement item = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/thead/tr/td[" + c + "]"));
            if (item.getText().equals("Status")) {
                String pending = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[" + c + "]")).getText();
                System.out.println(test + ": " +"Status: " + pending);
                Assert.assertEquals(pending, "Pending");
            }
            else if (item.getText().contains("Date")) {   //  The difference between local time and site time should be considered.
                String date = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr/td[" + c + "]")).getText();
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                System.out.println(test + ": " +"Date Added in site: " + date);
                System.out.println(test + ": " +"local date: " + formatter.format(new Date()));
                Assert.assertEquals(date, formatter.format(new Date()));
            }
        }
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    private String CreateRandomEmail() {
        Random r = new Random();
        String email = "";
        for (int i = 0; i < 8; i++) {
            email += (char) ('a' + r.nextInt(26));
        }
        return email + "@yahoo.com";
    }
}
