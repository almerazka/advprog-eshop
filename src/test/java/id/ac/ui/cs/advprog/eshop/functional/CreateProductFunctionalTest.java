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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    // Mengatur baseUrl sebelum setiap pengujian
    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    // Menguji apakah halaman Create Product dapat diakses dengan benar
    @Test
    void createProductPage_IsCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");
        assertEquals("Create New Product", driver.getTitle());
    }

    // Menguji apakah produk dapat dibuat dan muncul di daftar produk
    @Test
    void testCreateProduct_Success(ChromeDriver driver) {
        // Buka halaman Create Product
        driver.get(baseUrl + "/product/create");

        // Input nama produk
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Sampo Cap Bambang");

        // Input jumlah produk
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("100");

        // Klik tombol Submit
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Pastikan user diarahkan ke halaman Product List
        assertTrue(driver.getCurrentUrl().contains("/product/list"));

        // Ambil nama produk pertama di tabel
        String productName = driver.findElement(By.xpath("//tbody/tr[1]/td[1]")).getText();
        String productQuantity = driver.findElement(By.xpath("//tbody/tr[1]/td[2]")).getText();

        // Pastikan produk yang ditambahkan muncul di daftar
        assertEquals("Sampo Cap Bambang", productName);
        assertEquals("100", productQuantity); // Pastikan tidak ada format angka "100.0"
    }
}