package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

public class PaymentCOD extends Payment {

    public PaymentCOD(String id, String method, Map<String, String> paymentData) {
        super(id, method, paymentData);


    }

    private boolean isValidPaymentData(Map<String, String> paymentData) {
      return false;
    }
}
