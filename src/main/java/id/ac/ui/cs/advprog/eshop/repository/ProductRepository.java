package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        // Validasi input sebelum menyimpan produk
        product.setProductName(validateAndSanitizeName(product.getProductName()));
        product.setProductQuantity(validateQuantity(product.getProductQuantity()));

        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findProductById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public Product edit(String productId, Product newProduct) {
        Product productToEdit = findProductById(productId);

        if (productToEdit == null) {
            return null;
        }

        // Validasi dan update data
        productToEdit.setProductName(validateAndSanitizeName(newProduct.getProductName()));
        productToEdit.setProductQuantity(validateQuantity(newProduct.getProductQuantity()));
        return productToEdit;
    }

    // Metode untuk validasi dan sanitasi nama produk
    private String validateAndSanitizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Product not found";
        }
        return name.replaceAll("[<>%$]", "");
    }

    // Metode untuk validasi kuantitas produk (tidak boleh huruf)
    private double validateQuantity(double quantity) {
        if (Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            return 0; // Jika bukan angka, ubah menjadi 0
        }
        return Math.max(quantity, 0); // Jika negatif, ubah ke 0
    }

    public void delete(String productId) {
        Product productToDelete = findProductById(productId);
        productData.remove(productToDelete);
    }
}