package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentCODTest extends PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        super.setUp();
        paymentData = new HashMap<>();
    }

    // Happy path test: Ensures COD payment is successful with valid data
    @Test
    void testValidCODPayment() {
        paymentData.put("address", "Jl. Merdeka No.1");
        paymentData.put("deliveryFee", "10000");

        PaymentCOD payment = new PaymentCOD("1", PaymentMethod.COD.name(), paymentData);
        assertEquals(PaymentStatus.PENDING, payment.getStatus());
    }

    // Unhappy path test: Ensures payment is rejected if the address is empty
    @Test
    void testEmptyAddress() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "10000");

        PaymentCOD payment = new PaymentCOD("2", PaymentMethod.COD.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Ensures payment is rejected if the delivery fee is empty
    @Test
    void testEmptyDeliveryFee() {
        paymentData.put("address", "Jl. Merdeka No.1");
        paymentData.put("deliveryFee", "");

        PaymentCOD payment = new PaymentCOD("3", PaymentMethod.COD.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Ensures payment is rejected if the address is `null`
    @Test
    void testNullAddress() {
        paymentData.put("address", null);
        paymentData.put("deliveryFee", "10000");

        PaymentCOD payment = new PaymentCOD("4", PaymentMethod.COD.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Ensures payment is rejected if the delivery fee is `null`
    @Test
    void testNullDeliveryFee() {
        paymentData.put("address", "Jl. Merdeka No.1");
        paymentData.put("deliveryFee", null);

        PaymentCOD payment = new PaymentCOD("5", PaymentMethod.COD.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Ensures payment is rejected if both address and delivery fee are empty
    @Test
    void testBothEmptyFields() {
        paymentData.put("address", "");
        paymentData.put("deliveryFee", "");

        PaymentCOD payment = new PaymentCOD("6", PaymentMethod.COD.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }
}