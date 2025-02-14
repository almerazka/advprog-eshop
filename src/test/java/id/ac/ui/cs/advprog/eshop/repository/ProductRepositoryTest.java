package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = initiateProduct("eb558e9f-1c39-460e-8860-71af6af63bd", "Sampo Cap Bambang", 100);
    }

    private Product initiateProduct(String id, String name, double quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        productRepository.create(product);
        return product;
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        productRepository = new ProductRepository();
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        productRepository = new ProductRepository();
        Product product1 = initiateProduct("eb558e9f-1c39-460e-8860-71af6af63bd", "Sampo Cap Bambang", 100);
        Product product2 = initiateProduct("a0f9de46-90b1-437d-a0bf-d0821dde9096", "Sampo Cap Usep", 50);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct_Success() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(75);

        Product editedProduct = productRepository.edit(testProduct.getProductId(), updatedProduct);

        assertNotNull(editedProduct);
        assertEquals("Sampo Cap Usep", editedProduct.getProductName());
        assertEquals(75, editedProduct.getProductQuantity());
    }

    @Test
    void testEditProduct_NotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Sampo Tidak Ada");
        updatedProduct.setProductQuantity(20);

        Product result = productRepository.edit(UUID.randomUUID().toString(), updatedProduct);

        assertNull(result);
    }

    @Test
    void testEditProduct_NullNewProduct() {
        assertNull(productRepository.edit(testProduct.getProductId(), null));
    }

    @Test
    void testDeleteProduct_Success() {
        productRepository.delete(testProduct.getProductId());
        assertNull(productRepository.findProductById(testProduct.getProductId()));
    }

    @Test
    void testDeleteProduct_NotFound() {
        productRepository.delete(UUID.randomUUID().toString());
        assertNotNull(productRepository.findProductById(testProduct.getProductId()));
    }

    @Test
    void testCreateProduct_NegativeQuantity_ShouldThrowException() {
        Product invalidProduct = new Product();
        invalidProduct.setProductId("invalid-123");
        invalidProduct.setProductName("Invalid Product");
        invalidProduct.setProductQuantity(-10);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.create(invalidProduct));

        assertEquals("Product quantity cannot be negative", exception.getMessage());
    }

    @Test
    void testEditProduct_NegativeQuantity_ShouldThrowException() {
        Product updatedProduct = new Product();
        updatedProduct.setProductName("Wrong Product");
        updatedProduct.setProductQuantity(-5);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.edit(testProduct.getProductId(), updatedProduct));

        assertEquals("Product quantity cannot be negative", exception.getMessage());
    }
}
