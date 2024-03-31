package br.com.intelligym.endpoint.payment;

import br.com.intelligym.dto.paymentsolver.PaymentWithCustomerDTO;
import br.com.intelligym.endpoint.BasePaymentEndpoint;
import br.com.intelligym.usecase.payment.GetAllPayment;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllPaymentEndpoint extends BasePaymentEndpoint {
    private final GetAllPayment useCase;

    public GetAllPaymentEndpoint(GetAllPayment useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<PaymentWithCustomerDTO> paymentResponseDTOList) {
        return ResponseEntity.ok(paymentResponseDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all payments")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllPayment.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllPayment.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllPayment.OutputPort.NoResults __ -> noResultResponse();
            case GetAllPayment.OutputPort.NotAuthorized __ -> ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            case GetAllPayment.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }

}
