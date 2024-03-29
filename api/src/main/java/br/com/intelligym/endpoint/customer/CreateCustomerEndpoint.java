package br.com.intelligym.endpoint.customer;

import br.com.intelligym.endpoint.BaseCustomerEndpoint;
import br.com.intelligym.model.customer.Address;
import br.com.intelligym.model.customer.Contact;
import br.com.intelligym.model.enums.Plan;
import br.com.intelligym.usecase.customer.CreateCustomer;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CreateCustomerEndpoint extends BaseCustomerEndpoint {
    private final CreateCustomer useCase;

    public CreateCustomerEndpoint(CreateCustomer useCase) {
        this.useCase = useCase;
    }

    public record Request(String name, String cpf, Address address, Contact contact, LocalDate dateOfBirth, Plan plan) { }

    @Operation(summary = "Create a customer")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute (@RequestBody @Valid Request request) {
        CreateCustomer.OutputPort result = this.useCase.execute(new CreateCustomer.InputPort(request.name(), request.cpf(), request.address(), request.contact(), request.dateOfBirth(), request.plan()));

        return switch (result) {
            case CreateCustomer.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateCustomer.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
