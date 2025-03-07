package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    @Setter
    String id;
    String method;
    PaymentStatus status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.paymentData = paymentData;
        this.status = PaymentStatus.PENDING;

        String[] methodList = {"VOUCHER", "COD"};
        if (Arrays.stream(methodList).noneMatch(item -> item.equals(method))) {
            throw new IllegalArgumentException("Invalid payment method");
        } else {
            this.method = method;
        }
    }

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this(id, method, paymentData);
        setStatus(status);
    }

    public void setStatus(String status) {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        } else {
            this.status = PaymentStatus.valueOf(status);
        }
    }
}



