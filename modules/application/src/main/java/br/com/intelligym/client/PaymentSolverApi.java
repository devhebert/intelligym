package br.com.intelligym.client;

import br.com.intelligym.dto.paymentsolver.ResponsePaymentSolverApi;

public interface PaymentSolverApi {
    ResponsePaymentSolverApi processPayment(PaymentRequest paymentRequest);
}
