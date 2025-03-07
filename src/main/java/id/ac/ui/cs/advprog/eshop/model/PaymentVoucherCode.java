package id.ac.ui.cs.advprog.eshop.model;

import enums.PaymentStatus;
import java.util.Map;

public class PaymentVoucherCode extends Payment {

    public PaymentVoucherCode(String id, String method, Map<String, String> paymentData) {
        super(id, method, paymentData);

        String voucherCode = paymentData.get("voucherCode");
        if (isValidVoucherCode(voucherCode)) {
            setStatus(PaymentStatus.SUCCESS.name());
        } else {
            setStatus(PaymentStatus.REJECTED.name());
        }
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        String numericalPart = voucherCode.substring(5);
        int countNumericalChars = 0;
        for (char c : numericalPart.toCharArray()) {
            if (Character.isDigit(c)) {
                countNumericalChars++;
            }
        }

        return countNumericalChars == 8;
    }
}
