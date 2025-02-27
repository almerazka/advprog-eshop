package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class HomePageFunctionalTest {
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

    // Menguji apakah judul halaman utama sesuai dengan yang diharapkan
    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();
        // Verify
        assertEquals("ADV Shop", pageTitle);
    }

    // Menguji apakah pesan selamat datang pada halaman utama sesuai dengan yang diharapkan
    @Test
    void welcomeMessage_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise
        driver.get(baseUrl);
        String welcomeMessage = driver.findElement(By.tagName("h3")).getText();
        // Verify
        assertEquals("Welcome", welcomeMessage);
    }
}