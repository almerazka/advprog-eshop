package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd");
        testProduct.setProductName("Sampo Cap Bambang");
        testProduct.setProductQuantity(100);
    }

    // Menguji apakah produk dapat dibuat dengan benar
    @Test
    void testCreateProduct() {
        when(productRepository.create(testProduct)).thenReturn(testProduct);

        Product result = productService.create(testProduct);

        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.getProductId());
        assertEquals(testProduct.getProductName(), result.getProductName());
        assertEquals(testProduct.getProductQuantity(), result.getProductQuantity());
        verify(productRepository, times(1)).create(testProduct);
    }

    // Menguji apakah semua produk dapat ditemukan
    @Test
    void testFindAllProducts() {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(testProduct);
        mockProducts.add(new Product("Sampo Cap Usep", 50));

        Iterator<Product> mockIterator = mockProducts.iterator();
        when(productRepository.findAll()).thenReturn(mockIterator);

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    // Menguji apakah produk dapat ditemukan berdasarkan ID (sukses)
    @Test
    void testFindProductById_Success() {
        when(productRepository.findProductById(testProduct.getProductId())).thenReturn(testProduct);

        Product result = productService.findProductById(testProduct.getProductId());

        assertNotNull(result);
        assertEquals(testProduct.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findProductById(testProduct.getProductId());
    }

    // Menguji jika produk tidak ditemukan berdasarkan ID
    @Test
    void testFindProductById_NotFound() {
        when(productRepository.findProductById("invalid-id")).thenReturn(null);

        Product result = productService.findProductById("invalid-id");

        assertNull(result);
        verify(productRepository, times(1)).findProductById("invalid-id");
    }

    // Menguji apakah produk dapat diedit dengan benar
    @Test
    void testEditProduct_Success() {
        Product updatedProduct = new Product("Sampo Cap Baru", 75);
        when(productRepository.edit(testProduct.getProductId(), updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.edit(testProduct.getProductId(), updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Baru", result.getProductName());
        assertEquals(75, result.getProductQuantity());
        verify(productRepository, times(1)).edit(testProduct.getProductId(), updatedProduct);
    }

    // Menguji jika produk yang ingin diedit tidak ditemukan
    @Test
    void testEditProduct_NotFound() {
        Product updatedProduct = new Product("Tidak Ada", 20);
        when(productRepository.edit("invalid-id", updatedProduct)).thenReturn(null);

        Product result = productService.edit("invalid-id", updatedProduct);

        assertNull(result);
        verify(productRepository, times(1)).edit("invalid-id", updatedProduct);
    }

    // Menguji apakah produk dapat dihapus dengan benar
    @Test
    void testDeleteProduct() {
        doNothing().when(productRepository).delete(testProduct.getProductId());

        productService.delete(testProduct.getProductId());

        verify(productRepository, times(1)).delete(testProduct.getProductId());
    }
}
