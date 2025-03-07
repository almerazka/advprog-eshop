package id.ac.ui.cs.advprog.eshop.service;

import enums.PaymentStatus;
import enums.PaymentMethod;
import enums.OrderStatus;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        orders = new ArrayList<>();
        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");
        orders.add(order);

        payments = new ArrayList<>();
        Map<String, String> paymentData1 = Map.of("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment(order.getId(), PaymentMethod.VOUCHER.getValue(), paymentData1);
        payments.add(payment1);
    }

    // Happy path test: Create and add a new Payment to PaymentRepository
    @Test
    void testAddPayment() {
        Order order = orders.getFirst();
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), Map.of("voucherCode", "ESHOP1234ABC5678"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    // Happy path test: Update Payment status to "SUCCESS" and ensure Order status is also updated to "SUCCESS"
    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
    }

    // Happy path test: Update Payment status to "REJECTED" and ensure Order status is updated to "FAILED"
    @Test
    void testSetStatusRejected() {
        Payment payment = payments.getFirst();
        Order order = orders.getFirst();
        doReturn(order).when(orderRepository).findById(payment.getId());
        doReturn(order).when(orderRepository).save(order);
        doReturn(payment).when(paymentRepository).save(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());
        verify(orderRepository, times(1)).save(order);
        verify(paymentRepository, times(1)).save(payment);
        assertEquals(PaymentStatus.REJECTED, result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
    }

    // Unhappy path test: Try to update Payment with an invalid status and expect an exception
    @Test
    void testSetStatusInvalidStatus() {
        Payment payment = payments.getFirst();
        assertThrows(IllegalArgumentException.class, () ->
                paymentService.setStatus(payment, "INVALID_STATUS"));
    }

    // Happy path test: Retrieve all stored Payments from PaymentRepository
    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).findAll();
        assertEquals(payments, result);
    }

    // Happy path test: Retrieve a Payment by its paymentId
    @Test
    void testGetPayment() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        verify(paymentRepository, times(1)).findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    // Unhappy path test: Ensure getAllPayments() returns an empty list when no payments exist
    @Test
    void testGetAllPaymentsWhenEmpty() {
        doReturn(new ArrayList<>()).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();
        verify(paymentRepository, times(1)).findAll();
        assertTrue(result.isEmpty());
    }

    // Unhappy path test: Attempt to retrieve a non-existent Payment (should return null)
    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).findById("invalid-id");
        assertNull(paymentService.getPayment("invalid-id"));
    }
}
