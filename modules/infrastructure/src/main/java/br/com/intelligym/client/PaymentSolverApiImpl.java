package br.com.intelligym.client;

import br.com.intelligym.dto.paymentsolver.ResponsePaymentSolverApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-service", url = "${payment-solver.url}")
public interface PaymentSolverApiImpl extends PaymentSolverApi {
    @PostMapping("create")
    ResponsePaymentSolverApi processPayment(PaymentRequest paymentRequest);
}
