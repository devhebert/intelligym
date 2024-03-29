package br.com.intelligym.usecase.customer;

import br.com.intelligym.exception.customer.ErrorMessages;
import br.com.intelligym.mapper.customer.CustomerMapper;
import br.com.intelligym.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

public class GetCustomerByIdImpl implements GetCustomerById {
    private final Logger logger = Logger.getLogger(GetCustomerByIdImpl.class.getName());
    private final CustomerRepository customerRepository;

    public GetCustomerByIdImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            Optional<OutputPort> result =  this.customerRepository.findById(inputPort.customerId())
                    .map(customer -> new OutputPort.Ok(CustomerMapper.INSTANCE.customerToCustomerDTO(customer)));

            return result.orElse(new OutputPort.Error(ErrorMessages.CUSTOMER_NOT_FOUND));
        } catch (Exception e) {
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
