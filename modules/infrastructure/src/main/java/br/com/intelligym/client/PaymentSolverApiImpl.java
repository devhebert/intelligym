package br.com.intelligym.client;

import br.com.intelligym.client.paymentsolver.PaymentRequest;
import br.com.intelligym.client.paymentsolver.PaymentSolverApi;
import br.com.intelligym.dto.paymentsolver.PaymentDTO;
import br.com.intelligym.dto.paymentsolver.ResponsePaymentSolverApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "api-service", url = "${payment-solver.url}")
public interface PaymentSolverApiImpl extends PaymentSolverApi {
    @PostMapping("create")
    ResponsePaymentSolverApi processPayment(PaymentRequest paymentRequest);

    @GetMapping("get-all")
    List<PaymentDTO> getAllPayments();

    @GetMapping("/{id}")
    PaymentDTO getPaymentById(@PathVariable UUID id);
}