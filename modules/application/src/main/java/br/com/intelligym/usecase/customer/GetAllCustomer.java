package br.com.intelligym.usecase.customer;

import br.com.intelligym.dto.customer.CustomerDTO;

import java.util.List;

public interface GetAllCustomer {
    sealed interface OutputPort permits OutputPort.Ok, OutputPort.NoResult {
        record Ok(List<CustomerDTO> list) implements OutputPort {}
        record NoResult() implements OutputPort {}
    }

    OutputPort execute();
}
