package br.com.intelligym.usecase.customer;

import br.com.intelligym.dto.customer.CustomerDTO;

import java.util.UUID;

public interface GetCustomerById {
    public record InputPort(UUID customerId) { }

    sealed interface OutputPort permits OutputPort.Error, OutputPort.NotFound, OutputPort.Ok {
        public final record Ok(CustomerDTO customer) implements OutputPort { }
        public final record NotFound() implements OutputPort { }
        public final record Error(String message) implements OutputPort { }
    }

    OutputPort execute(InputPort inputPort);
}
