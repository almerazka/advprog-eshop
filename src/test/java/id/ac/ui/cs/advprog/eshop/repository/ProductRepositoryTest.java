package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Tidak ada inisialisasi yang diperlukan untuk saat ini
    }

    // Menguji apakah produk dapat dibuat dan ditemukan dalam daftar
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    // Menguji jika daftar produk kosong
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    // Menguji apakah banyak produk dapat ditemukan dalam daftar
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de45-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    // Menguji apakah banyak produk dapat ditemukan dalam daftar
    @Test
    void testFindProductById() {
        Product product = new Product();
        product.setProductId("test-id-123");
        product.setProductName("Test Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product foundProduct = productRepository.findProductById("test-id-123");
        assertNotNull(foundProduct);
        assertEquals("test-id-123", foundProduct.getProductId());
        assertEquals("Test Product", foundProduct.getProductName());
        assertEquals(10, foundProduct.getProductQuantity());

        Product notFoundProduct = productRepository.findProductById("non-existent-id");
        assertNull(notFoundProduct);
    }

    // Menguji apakah produk dapat diperbarui dengan informasi baru
    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId("edit-test-id");
        product.setProductName("Original Name");
        product.setProductQuantity(5);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(10);

        Product editedProduct = productRepository.edit("edit-test-id", updatedProduct);
        assertNotNull(editedProduct);
        assertEquals("edit-test-id", editedProduct.getProductId());
        assertEquals("Updated Name", editedProduct.getProductName());
        assertEquals(10, editedProduct.getProductQuantity());

        Product retrievedProduct = productRepository.findProductById("edit-test-id");
        assertEquals("Updated Name", retrievedProduct.getProductName());
        assertEquals(10, retrievedProduct.getProductQuantity());
    }

    // Menguji apakah pengeditan produk gagal jika ID tidak ditemukan
    @Test
    void testEditNonExistentProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(10);

        Product result = productRepository.edit("non-existent-id", updatedProduct);
        assertNull(result);
    }

    // Menguji apakah pengeditan produk gagal jika input produk null
    @Test
    void testEditWithNullProduct() {
        Product product = new Product();
        product.setProductId("null-test-id");
        product.setProductName("Test Product");
        product.setProductQuantity(5);
        productRepository.create(product);

        Product result = productRepository.edit("null-test-id", null);
        assertNull(result);
    }

    // Menguji apakah produk dapat dihapus dengan benar
    @Test
    void testDeleteProduct() {
        Product product = new Product();
        product.setProductId("delete-test-id");
        product.setProductName("Product to Delete");
        product.setProductQuantity(3);
        productRepository.create(product);

        assertNotNull(productRepository.findProductById("delete-test-id"));

        productRepository.delete("delete-test-id");

        assertNull(productRepository.findProductById("delete-test-id"));
    }

    // Menguji apakah penghapusan produk yang tidak ada tidak menimbulkan error
    @Test
    void testDeleteNonExistentProduct() {
        assertDoesNotThrow(() -> productRepository.delete("non-existent-id"));
    }

    // Menguji apakah nama produk kosong akan diubah menjadi "Product not found"
    @Test
    void testCreateWithEmptyName() {
        Product product = new Product();
        product.setProductId("empty-name-id");
        product.setProductName("");
        product.setProductQuantity(5);

        Product savedProduct = productRepository.create(product);
        assertEquals("Product not found", savedProduct.getProductName());
    }

    // Menguji apakah nama produk kosong akan diubah menjadi "Product not found"
    @Test
    void testCreateWithNullName() {
        Product product = new Product();
        product.setProductId("null-name-id");
        product.setProductName(null);
        product.setProductQuantity(5);

        Product savedProduct = productRepository.create(product);
        assertEquals("Product not found", savedProduct.getProductName());
    }

    // Menguji apakah karakter spesial dalam nama produk dihapus
    @Test
    void testCreateWithSpecialCharsInName() {
        Product product = new Product();
        product.setProductId("special-chars-id");
        product.setProductName("Product<with>special%chars$");
        product.setProductQuantity(5);

        Product savedProduct = productRepository.create(product);
        assertEquals("Productwithspecialchars", savedProduct.getProductName());
    }

    // Menguji apakah produk dengan kuantitas negatif akan menimbulkan exception
    @Test
    void testCreateWithNegativeQuantity() {
        Product product = new Product();
        product.setProductId("negative-quantity-id");
        product.setProductName("Negative Quantity Product");
        product.setProductQuantity(-10);

        assertThrows(IllegalArgumentException.class, () -> productRepository.create(product));
    }

    // Menguji apakah produk dengan NaN sebagai kuantitas akan diubah menjadi 0
    @Test
    void testCreateWithNaNQuantity() {
        Product product = new Product();
        product.setProductId("nan-quantity-id");
        product.setProductName("NaN Quantity Product");
        product.setProductQuantity(Double.NaN);

        Product savedProduct = productRepository.create(product);
        assertEquals(0, savedProduct.getProductQuantity());
    }

    // Menguji apakah produk dengan kuantitas infinity akan diubah menjadi 0
    @Test
    void testCreateWithInfiniteQuantity() {
        Product product = new Product();
        product.setProductId("infinite-quantity-id");
        product.setProductName("Infinite Quantity Product");
        product.setProductQuantity(Double.POSITIVE_INFINITY);

        Product savedProduct = productRepository.create(product);
        assertEquals(0, savedProduct.getProductQuantity());
    }
}