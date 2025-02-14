package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@DirtiesContext
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class EditProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUp(ChromeDriver driver) {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);
        createProduct(driver, "Sample Product", 50);
    }

    private void createProduct(ChromeDriver driver, String name, int quantity) {
        driver.get(baseUrl);
        driver.findElement(By.id("nameInput")).sendKeys(name);
        driver.findElement(By.id("quantityInput")).sendKeys(String.valueOf(quantity));
        driver.findElement(By.tagName("button")).click();
    }

    @Test
    void editProductTest(ChromeDriver driver) {
        driver.get(String.format("%s:%d/product/list", testBaseUrl, serverPort));
        driver.findElement(By.partialLinkText("Edit")).click();

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Updated Product");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("99");

        driver.findElement(By.tagName("button")).click();

        assertTrue(driver.getPageSource().contains("Updated Product"));
        assertTrue(driver.getPageSource().contains("99"));
    }
}