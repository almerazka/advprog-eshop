package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class HomePageControllerTest {

    @InjectMocks
    private HomePageController homePageController;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        // Tidak ada inisialisasi yang diperlukan untuk saat ini
    }

    // Menguji apakah metode homePage() mengembalikan string yang sesuai
    @Test
    void testHomePageReturnString() {
        String result = homePageController.homePage();
        assertEquals("HomePage", result);
    }

    // Menguji apakah permintaan GET ke "/" berhasil dan mengandung teks yang diharapkan
    @Test
    void testHomePageRequest() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())  // Pastikan HTTP status 200 (OK)
                .andExpect(content().string(containsString("Welcome")));
    }
}
