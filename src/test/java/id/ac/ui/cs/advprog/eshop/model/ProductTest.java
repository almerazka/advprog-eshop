package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ProductTest {
    Product product;
    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("ab558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }
    @Test
    void testGetProductId() {
        assertEquals("ab558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }
    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }
    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }
    @Test
    void testDefaultConstructor_GeneratesUUID() {
        Product newProduct = new Product();
        assertNotNull(newProduct.getProductId()); // UUID should not be null
        assertFalse(newProduct.getProductId().isEmpty()); // UUID should not be empty
    }
    @Test
    void testSetters_UpdateValuesCorrectly() {
        product.setProductName("Shampoo Clean");
        product.setProductQuantity(50);

        assertEquals("Shampoo Clean", product.getProductName());
        assertEquals(50, product.getProductQuantity());
    }
}