package br.com.intelligym.dto.paymentsolver;

import br.com.intelligym.model.enums.PaymentType;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentDTO(UUID customerId, UUID planId, PaymentType paymentType, double value, String description, LocalDateTime paymentDate, Boolean isPaid) {
}
