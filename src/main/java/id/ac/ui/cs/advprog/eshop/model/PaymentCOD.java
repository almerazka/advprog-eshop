package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import java.util.Map;

public class PaymentCOD extends Payment {

    public PaymentCOD(String id, String method, Map<String, String> paymentData) {
        super(id, method, paymentData);

        if (isValidPaymentData(paymentData)) {
            setStatus(PaymentStatus.PENDING.name());
        } else {
            setStatus(PaymentStatus.REJECTED.name());
        }
    }

    private boolean isValidPaymentData(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        return address != null && !address.isEmpty() && deliveryFee != null && !deliveryFee.isEmpty();
    }
}
