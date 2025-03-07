package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Payment payment;
    private String id;
    private String method;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        id = "1";
        method = "COD";
        paymentData = new HashMap<>();
        paymentData.put("address", "Jl. Merdeka No.1");
        payment = new Payment(id, method, paymentData);
    }

    // Happy path test: Test to create a payment with default status "PENDING"
    @Test
    void testCreatePaymentDefaultStatus() {
        assertEquals(id, payment.getId());
        assertEquals(method, payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("Jl. Merdeka No.1", payment.getPaymentData().get("address"));
    }

    // Happy path test: Test to create a payment with status "SUCCESS"
    @Test
    void testCreatePaymentSuccessStatus() {
        payment = new Payment("2", "VOUCHER", "SUCCESS", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    // Happy path test: Test to create a payment with status "REJECTED"
    @Test
    void testCreatePaymentRejectedStatus() {
        payment = new Payment("3", "COD", "REJECTED", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Unhappy path test: Test to create a payment with an invalid status
    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () ->
                new Payment("4", "COD", "INVALID_STATUS", paymentData));
    }

    // Happy path test: Test to set status to "SUCCESS"
    @Test
    void testSetStatusToSuccess() {
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    // Unhappy path test: Test to set an invalid status
    @Test
    void testSetStatusToInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () ->
                payment.setStatus("INVALID_STATUS"));
    }

    // Unhappy path test: Test to create a payment with an invalid method
    @Test
    void testCreatePaymentInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () ->
                new Payment("5", "MEOWW", paymentData));
    }
}