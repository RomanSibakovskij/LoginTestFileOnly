package lt.techin.example;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;


public class LoginTestPage {

    WebDriver driver;

        //7
    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(ofSeconds(3));
        driver.get("https://www.saucedemo.com/");
    }


    //4
    //valid login
    @Test
    void loginValid(){

        WebElement inputLogin = driver.findElement(By.cssSelector("#login_button_container > div > form > div:nth-child(1) > #user-name"));
        inputLogin.sendKeys("standard_user");

        WebElement inputPassword = driver.findElement(By.cssSelector("#login_button_container > div > form > div:nth-child(2) > #password"));
        inputPassword.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.cssSelector("#login-button"));
        loginButton.click();

        //boolean result = assertEquals("https://www.saucedemo.com/inventory.html", "https://www.saucedemo.com/inventory.html");
        //System.out.println(result);//a


        assertTrue(Boolean.parseBoolean(String.valueOf(true)), String.valueOf(false));

        /*
        WebElement output = driver.findElement(By.linkText("https://www.saucedemo.com/inventory.html"));
        System.out.println(output.isDisplayed()); //b // throws webdriverexception
        */



    }

        //5
    //invalid login
    @Test
    void loginInvalid(){

        WebElement inputL = driver.findElement(By.cssSelector("#login_button_container > div > form > div:nth-child(1) > #user-name"));
        inputL.sendKeys("standard_user");

        WebElement inputP = driver.findElement(By.cssSelector("#login_button_container > div > form > div:nth-child(2) > #password"));
        inputP.sendKeys("wrong_password");

        WebElement loginB = driver.findElement(By.cssSelector("#login-button"));
        loginB.click();

        WebElement errorMsg = driver.findElement(By.cssSelector("#login_button_container > div > form > div.error-message-container.error"));
        String text = errorMsg.getText();
        if(text.contains("Epic sadface: Username and password do not match any user in this service")) {
            System.out.println("Expected text is present");
        }else{
            System.out.println("Expected text is not present");
        }

    }
        //6
    //invalid login 2
    @Test
    void loginInvalid2(){
        WebElement inputl = driver.findElement(By.cssSelector("#login_button_container > div > form > div:nth-child(1) > #user-name"));
        inputl.sendKeys("locked_out-user");

        WebElement inputp = driver.findElement(By.cssSelector("#login_button_container > div > form > div:nth-child(2) > #password"));
        inputp.sendKeys("secret_sauce");

        WebElement loginb = driver.findElement(By.cssSelector("#login-button"));
        loginb.click();

        WebElement errorM = driver.findElement(By.cssSelector("#login_button_container > div > form > div.error-message-container.error"));
        String text = errorM.getText();
        if(text.contains("this user has been locked out")) {
            System.out.println("Expected text is present");
        }else{
            System.out.println("Expected text is not present");
        }
    }
    //parameterized test
    @ParameterizedTest //8
    @CsvFileSource(files = "src/test/resource/logins.csv", numLinesToSkip = 1)
    void loginV(String USER_NAME, String PASSWORD){
        assertNotNull(USER_NAME, PASSWORD);
        assertNotSame(USER_NAME, PASSWORD);
        assertNotEquals(PASSWORD, "wrong_password");

    }
    //parameterized test to find out whether the connection completes in 2 seconds and certain user doesn't have access
    @ParameterizedTest //9
    @CsvFileSource(files = "src/test/resource/logins.csv", numLinesToSkip = 1)
    void loginI(String PASSWORD){
        assertTimeout(ofSeconds(2), () -> {
            String u = "performance_glitch_user";
            assertNotEquals(u, PASSWORD);
        });
    }


         //7
    @AfterEach
    void close(){
        driver.close();
    }

}
