package br.com.intelligym.client.paymentsolver;

import br.com.intelligym.model.enums.PaymentType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest implements Serializable {
    private UUID customerId;
    private UUID planId;
    private PaymentType paymentType;
    private BigDecimal value;
    private String description;

    public PaymentRequest() {
    }

    public PaymentRequest(UUID customerId, UUID planId, PaymentType paymentType, BigDecimal value, String description) {
        this.customerId = customerId;
        this.planId = planId;
        this.paymentType = paymentType;
        this.value = value;
        this.description = description;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getPlanId() {
        return planId;
    }

    public void setPlanId(UUID planId) {
        this.planId = planId;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
