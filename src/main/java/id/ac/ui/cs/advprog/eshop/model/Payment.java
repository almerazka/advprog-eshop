package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Builder
@Getter
public class Payment {
    @Setter
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.paymentData = paymentData;
        this.status = "PENDING";

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
        String[] statusList = {"PENDING", "REJECTED", "SUCCESS"};
        if (Arrays.stream(statusList).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException();
        } else {
            this.status = status;
        }
    }
}


