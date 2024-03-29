package br.com.intelligym.usecase.customer;

import br.com.intelligym.model.customer.Address;
import br.com.intelligym.model.customer.Contact;
import br.com.intelligym.model.enums.Plan;

import java.time.LocalDate;
import java.util.UUID;

public interface CreateCustomer {
    public record InputPort(String name, String cpf, Address address, Contact contact, LocalDate dateOfBirth, Plan plan) { }

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort { }
        public final record Error(String message) implements OutputPort { }
    }

    OutputPort execute(InputPort inputPort);
}
