package br.com.intelligym.usecase.payment;

import br.com.intelligym.client.paymentsolver.PaymentSolverApi;
import br.com.intelligym.dto.customer.CustomerDTO;
import br.com.intelligym.dto.paymentsolver.PaymentDTO;
import br.com.intelligym.dto.paymentsolver.PaymentWithCustomerDTO;
import br.com.intelligym.mapper.customer.CustomerMapper;
import br.com.intelligym.model.customer.Customer;
import br.com.intelligym.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetAllPaymentImpl implements GetAllPayment {
    private final Logger logger = LoggerFactory.getLogger(GetAllPaymentImpl.class);
    private final PaymentSolverApi paymentSolverApi;
    private final CustomerRepository customerRepository;

    public GetAllPaymentImpl(PaymentSolverApi paymentSolverApi, CustomerRepository customerRepository) {
        this.paymentSolverApi = paymentSolverApi;
        this.customerRepository = customerRepository;
    }

@Transactional(readOnly = true)
    public OutputPort execute() {
        try {
            List<PaymentDTO> payments = this.paymentSolverApi.getAllPayments();
            if (payments == null || payments.isEmpty()) {
                logger.error("No payments found");
                return new OutputPort.Error("No payments found");
            }

            List<Customer> customers = this.customerRepository.all();
            if (customers == null || customers.isEmpty()) {
                logger.error("No customers found");
                return new OutputPort.Error("No customers found");
            }

            List<PaymentWithCustomerDTO> paymentWithCustomerDTOList = payments.stream().map(payment -> {
                CustomerDTO customerDTO = customers.stream()
                        .filter(customer -> customer.getId().equals(payment.customerId()))
                        .findFirst()
                        .map(CustomerMapper.INSTANCE::customerToCustomerDTO)
                        .orElse(null);

                return new PaymentWithCustomerDTO(payment, customerDTO);
            }).collect(Collectors.toList());

            return new OutputPort.Ok(paymentWithCustomerDTOList);
        } catch (Exception e) {
            logger.error("Error on get all payments", e);
            return new OutputPort.NotAuthorized();
        }
    }
}
