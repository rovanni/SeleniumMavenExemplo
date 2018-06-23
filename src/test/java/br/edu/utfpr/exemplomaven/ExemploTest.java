package br.edu.utfpr.exemplomaven;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author andreendo
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExemploTest {

    /**
     * Vc precisa identificar onde estah o chromedriver. Baixar de:
     * https://sites.google.com/a/chromium.org/chromedriver/downloads
     *
     * Versão utilizada do chromedriver: 2.35.528139
     */
    //private static String CHROMEDRIVER_LOCATION = "/home/utfpr/install/selenium/chromedriver";
    private static final String CHROMEDRIVER_LOCATION = "C:\\Java\\chromedriver.exe";

    private static int scId = 0;

    WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_LOCATION);
    }

    @Before
    public void before() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //Opcao headless para MacOS e Linux
        //chromeOptions.addArguments("headless");
        chromeOptions.addArguments("window-size=1200x600");
        chromeOptions.addArguments("start-maximized");

        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void after() {
        driver.close();
    }

    @Test
    public void a_acessar_site_Ration() {
        driver.get("https://ration.io/");
        WebDriverWait wait;
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://ration.io/"));

        takeScreenShot();

    }

    @Test
    public void b_fazer_login_Ration() {
        String baseURL = "https://ration.io/login";
        String email = "rovanni@gmail.com";
        String password = "testesw2018";

        driver.get(baseURL);
        WebDriverWait wait;
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"login\"]/div/div/form/div[1]/input")));

        driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/div[1]/input")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/div[2]/input")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"login\"]/div/div/form/div[4]/button/span")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"header-account-menu-link\"]")));
        takeScreenShot();

    }

    @Test
    public void c_convidar_amigo() {
        b_fazer_login_Ration();
        String nome = "Luciano 01";
        String email = "rovanni@gmail.com";

        //Acessando pagina
        driver.get("https://ration.io/friends");

        WebDriverWait wait;
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"friends\"]/div/div[1]/div/button")));
        driver.findElement(By.xpath("//*[@id=\"friends\"]/div/div[1]/div/button")).click();

        //Enviando enviando_Convite_02      
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"friends\"]/div[2]/div[2]/div/form/div[2]/button[1]/span")));
        driver.findElement(By.xpath("//*[@id=\"friends\"]/div[2]/div[2]/div/form/div[1]/div[1]/div/div[1]/input")).sendKeys(nome);
        driver.findElement(By.xpath("//*[@id=\"friends\"]/div[2]/div[2]/div/form/div[1]/div[1]/div/div[2]/input")).sendKeys(email);
        driver.findElement(By.xpath("//*[@id=\"friends\"]/div[2]/div[2]/div/form/div[2]/button[1]/span")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"friends\"]/div/div[1]/div/button")));

        takeScreenShot();

    }

    @Test
    public void d_confirmar_pedido_amizade() {
        b_fazer_login_Ration();

        driver.get("https://ration.io/friends");

        driver.findElement(By.xpath("//*[@id=\"friends\"]/div/div[2]/table/tbody/tr[1]/td[3]/button")).click();
        WebDriverWait wait;
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://ration.io/friends"));

        takeScreenShot();

    }

    @Test
    public void e_remover_amigo() {
        b_fazer_login_Ration();
        driver.get("https://ration.io/friends");
        WebDriverWait wait;
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.urlToBe("https://ration.io/friends"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"friends\"]/div/div[2]/table/tbody/tr/td[3]/button")));
        driver.findElement(By.xpath("//*[@id=\"friends\"]/div/div[2]/table/tbody/tr/td[3]/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"friends\"]/div[2]/div[2]/div/form/div[2]/button[2]/span")));
        driver.findElement(By.xpath("//*[@id=\"friends\"]/div[2]/div[2]/div/form/div[2]/button[2]/span")).click();

        takeScreenShot();
    }

    private void takeScreenShot() {
        File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            scId++;
            FileUtils.copyFile(sourceFile, new File("./res/" + scId + ".png"));
        } catch (IOException e) {
        }
    }
}
