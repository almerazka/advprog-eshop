package enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("PENDING"),
    REJECTED("REJECTED"),
    SUCCESS("SUCCESS");

    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.name().equals(param)) {
                return true;
            }
        }
        return false;
    }
}
