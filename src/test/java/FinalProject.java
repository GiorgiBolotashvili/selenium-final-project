import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class FinalProject {
    private WebDriver driver;

    @BeforeMethod
    public void start(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @Test
    public void finalTest(){
        driver.get("http://tutorialsninja.com/demo/");
        driver.manage().window().maximize();
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

    }
    @AfterMethod
    public void tearDown(){
//        driver.quit();
    }
}
