package br.com.intelligym.dto.paymentsolver;

import br.com.intelligym.model.enums.PaymentType;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentDTO(UUID id, UUID customerId, UUID planId, PaymentType paymentType, BigDecimal value, String description) {}
