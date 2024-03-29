package br.com.intelligym.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum PaymentType {
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit Card"),
    MONEY("Money"),
    PIX("Pix");

    public final String type;

    PaymentType(String type) {
        this.type = type;
    }
}
