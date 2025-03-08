package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import enums.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentVoucherCodeTest extends PaymentTest {
    private Map<String, String> paymentData;

    @BeforeEach
    @Override
    void setUp() {
        super.setUp();
        paymentData = new HashMap<>();
    }

    // Happy path test: Test with a valid voucher code
    @Test
    void testValidVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        PaymentVoucherCode payment = new PaymentVoucherCode("f3978c74-088c-463f-8d3f-ffa1f4a61127", PaymentMethod.VOUCHER.name(), paymentData);
        assertEquals(PaymentStatus.SUCCESS, payment.getStatus());
    }

    // Unhappy path test: Test with a voucher code that is too short
    @Test
    void testShortVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234A5678");
        PaymentVoucherCode payment = new PaymentVoucherCode("f3978c74-088c-463f-8d3f-ffa1f4a61127", PaymentMethod.VOUCHER.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Test with a voucher code that doesn't start with "ESHOP"
    @Test
    void testDontStartWithESHOPVoucherCode() {
        paymentData.put("voucherCode", "1234ESHOPABC5678");
        PaymentVoucherCode payment = new PaymentVoucherCode("f3978c74-088c-463f-8d3f-ffa1f4a61127", PaymentMethod.VOUCHER.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Test with a voucher code that doesn't have exactly 8 numerical characters
    @Test
    void testNotEightNumericalCharsVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234ABCD678");
        PaymentVoucherCode payment = new PaymentVoucherCode("f3978c74-088c-463f-8d3f-ffa1f4a61127", PaymentMethod.VOUCHER.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Test with a null voucher code
    @Test
    void testNullVoucherCode() {
        paymentData.put("voucherCode", null);
        PaymentVoucherCode payment = new PaymentVoucherCode("f3978c74-088c-463f-8d3f-ffa1f4a61127", PaymentMethod.VOUCHER.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

    // Unhappy path test: Test with a voucher code that is too long
    @Test
    void testLongVoucherCode() {
        paymentData.put("voucherCode", "ESHOP1234567890123");
        PaymentVoucherCode payment = new PaymentVoucherCode("f3978c74-088c-463f-8d3f-ffa1f4a61127", PaymentMethod.VOUCHER.name(), paymentData);
        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
    }

}
