package br.com.intelligym.endpoint.customer;

import br.com.intelligym.dto.customer.CustomerDTO;
import br.com.intelligym.endpoint.BaseCustomerEndpoint;
import br.com.intelligym.usecase.customer.GetAllCustomer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCustomerEndpoint extends BaseCustomerEndpoint {
    private final GetAllCustomer useCase;

    public GetAllCustomerEndpoint(GetAllCustomer useCase) {
        this.useCase = useCase;
    }

    private ResponseEntity<?> okResponse(List<CustomerDTO> customerDTOList) {
        return ResponseEntity.ok(customerDTOList);
    }

    private ResponseEntity<?> noResultResponse() {
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all customers")
    @GetMapping(value = "get-all")
    public ResponseEntity<?> execute() {
        GetAllCustomer.OutputPort result = this.useCase.execute();

        return switch (result) {
            case GetAllCustomer.OutputPort.Ok ok -> okResponse(ok.list());
            case GetAllCustomer.OutputPort.NoResult noResult -> noResultResponse();
        };
    }
}
