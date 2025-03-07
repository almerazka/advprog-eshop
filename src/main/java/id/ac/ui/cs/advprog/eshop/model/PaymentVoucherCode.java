package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentVoucherCode extends Payment {


    public PaymentVoucherCode(String id, String method, Map<String, String> paymentData) {
        super(id, method, paymentData);

    }

    private boolean isValidVoucherCode(String voucherCode) {

        return false;
    }
}
