package br.com.intelligym.client.paymentsolver;

import br.com.intelligym.client.paymentsolver.PaymentRequest;
import br.com.intelligym.dto.paymentsolver.PaymentDTO;
import br.com.intelligym.dto.paymentsolver.ResponsePaymentSolverApi;

import java.util.List;

public interface PaymentSolverApi {
    ResponsePaymentSolverApi processPayment(PaymentRequest paymentRequest);
    List<PaymentDTO> getAllPayments();
}
