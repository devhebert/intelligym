package br.com.intelligym.usecase.customer;

import br.com.intelligym.mapper.customer.CustomerMapper;
import br.com.intelligym.model.customer.Customer;
import br.com.intelligym.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllCustomerImpl implements GetAllCustomer {
    private final CustomerRepository customerRepository;

    public GetAllCustomerImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public OutputPort execute() {
        try {
            List<Customer> customers = this.customerRepository.all();

            if (customers.isEmpty()) return new OutputPort.NoResult();

            return new OutputPort.Ok(customers.stream()
                    .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
                    .collect(Collectors.toList()));

        } catch (Exception e) {
            return new OutputPort.NoResult();
        }
    }
}
