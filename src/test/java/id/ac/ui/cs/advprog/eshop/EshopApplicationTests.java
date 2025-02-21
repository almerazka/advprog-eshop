package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

	@Test
	void contextLoads() {
		// Menguji apakah aplikasi dapat dijalankan tanpa error saat diinisialisasi oleh Spring Boot
		assertDoesNotThrow(() -> EshopApplication.main(new String[]{}));
	}
}
