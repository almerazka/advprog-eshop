package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        testProduct.setProductName("Sampo Cap Bambang");
        testProduct.setProductQuantity(100);
    }

    @Test
    void testCreateProductPage() {
        String result = productController.createProductPage(model);
        assertEquals("CreateProduct", result);
    }

    @Test
    void testCreateProductPost() {
        when(productService.create(any(Product.class))).thenReturn(testProduct);

        String result = productController.createProductPost(testProduct);

        assertEquals("redirect:list", result);
        verify(productService, times(1)).create(testProduct);
    }

    @Test
    void testProductListPage() {
        when(productService.findAll()).thenReturn(null);

        String result = productController.productListPage(model);

        assertEquals("ProductList", result);
        verify(model, times(1)).addAttribute(eq("products"), any());
    }

    @Test
    void testEditProductPage_Success() {
        when(productService.findProductById(testProduct.getProductId())).thenReturn(testProduct);

        String result = productController.editProductPage(testProduct.getProductId(), model);

        assertEquals("EditProduct", result);
        verify(model, times(1)).addAttribute("product", testProduct);
    }

    @Test
    void testEditProductPage_NotFound() {
        when(productService.findProductById("invalid-id")).thenReturn(null);

        String result = productController.editProductPage("invalid-id", model);

        assertEquals("redirect:/product/list", result);
    }

    @Test
    void testEditProduct_Success() {
        when(productService.edit(eq(testProduct.getProductId()), any(Product.class))).thenReturn(testProduct);

        String result = productController.editProduct(testProduct.getProductId(), testProduct, model);

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).edit(eq(testProduct.getProductId()), any(Product.class));
    }

    @Test
    void testEditProduct_NotFound() {
        when(productService.edit(eq("non-existent-id"), any(Product.class))).thenReturn(null);

        String result = productController.editProduct("non-existent-id", testProduct, model);

        assertEquals("EditProduct", result);
        verify(model, times(1)).addAttribute(eq("error"), contains("not found"));
    }

    @Test
    void testDeleteProduct_Success() {
        doNothing().when(productService).delete(testProduct.getProductId());

        String result = productController.deleteProduct(testProduct.getProductId());

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete(testProduct.getProductId());
    }

    @Test
    void testDeleteProduct_NotFound() {
        doNothing().when(productService).delete("non-existent-id");

        String result = productController.deleteProduct("non-existent-id");

        assertEquals("redirect:/product/list", result);
        verify(productService, times(1)).delete("non-existent-id");
    }
}