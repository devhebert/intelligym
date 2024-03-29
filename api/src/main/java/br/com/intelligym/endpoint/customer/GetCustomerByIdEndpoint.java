package br.com.intelligym.endpoint.customer;

import br.com.intelligym.endpoint.BaseCustomerEndpoint;
import br.com.intelligym.usecase.customer.GetCustomerById;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetCustomerByIdEndpoint extends BaseCustomerEndpoint {
    private final GetCustomerById useCase;

    public GetCustomerByIdEndpoint(GetCustomerById useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get a customer by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute (@PathVariable UUID id) {
        GetCustomerById.OutputPort result = this.useCase.execute(new GetCustomerById.InputPort(id));

        return switch (result) {
            case GetCustomerById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetCustomerById.OutputPort.NotFound notFound -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
            case GetCustomerById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }

}
