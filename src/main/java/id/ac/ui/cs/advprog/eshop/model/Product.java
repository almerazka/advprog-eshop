package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private double productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    // Constructor with parameters
    public Product(String productName, double productQuantity) {
        this.productId = UUID.randomUUID().toString();
        this.productName = productName;
        this.productQuantity = productQuantity;
    }
}