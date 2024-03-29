package br.com.intelligym.endpoint.customer;

import br.com.intelligym.endpoint.BaseCustomerEndpoint;
import br.com.intelligym.model.customer.Address;
import br.com.intelligym.model.customer.Contact;
import br.com.intelligym.model.enums.Plan;
import br.com.intelligym.usecase.customer.UpdateCustomer;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
public class UpdateCustomerEndpoint extends BaseCustomerEndpoint {
    private final UpdateCustomer useCase;

    public UpdateCustomerEndpoint(UpdateCustomer useCase) {
        this.useCase = useCase;
    }

    public record Request(String name, String cpf, Address address, Contact contact, LocalDate dateOfBirth, Plan plan) { }

    @Operation(summary = "Update a customer")
    @PutMapping("/{id}")
    public ResponseEntity<?> execute (@PathVariable UUID id, @RequestBody @Valid Request request) {
        UpdateCustomer.OutputPort result = this.useCase.execute(new UpdateCustomer.InputPort(id, request.name(), request.cpf(), request.address(), request.contact(), request.dateOfBirth(), request.plan()));

        return switch (result) {
            case UpdateCustomer.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case UpdateCustomer.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }

}
