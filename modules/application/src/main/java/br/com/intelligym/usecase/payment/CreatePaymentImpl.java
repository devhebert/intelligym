package br.com.intelligym.usecase.payment;

import br.com.intelligym.client.messaginsolver.MessagingRequest;
import br.com.intelligym.client.messaginsolver.MessagingSolverApi;
import br.com.intelligym.client.paymentsolver.PaymentRequest;
import br.com.intelligym.client.paymentsolver.PaymentSolverApi;
import br.com.intelligym.dto.paymentsolver.ResponsePaymentSolverApi;
import br.com.intelligym.exception.payment.ErrorMessages;
import br.com.intelligym.model.customer.Customer;
import br.com.intelligym.repository.CustomerRepository;
import br.com.intelligym.usecase.customer.CreateCustomerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreatePaymentImpl implements CreatePayment {
    private final Logger logger = LoggerFactory.getLogger(CreateCustomerImpl.class);
    private final CustomerRepository customerRepository;
    private final PaymentSolverApi paymentSolverApi;
    private final MessagingSolverApi messagingSolverApi;

    public CreatePaymentImpl(CustomerRepository customerRepository, PaymentSolverApi paymentSolverApi, MessagingSolverApi messagingSolverApi) {
        this.customerRepository = customerRepository;
        this.paymentSolverApi = paymentSolverApi;
        this.messagingSolverApi = messagingSolverApi;
    }

    @Transactional
    public OutputPort execute(InputPort input) {
        try {
            if (input == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            OutputPort validateInput = this.isValidInput(input);
            if (validateInput instanceof OutputPort.Error) return validateInput;

            Optional<Customer> customerOptional = this.customerRepository.findById(input.customerId());
            if (customerOptional.isEmpty()) return new OutputPort.Error(ErrorMessages.CUSTOMER_NOT_FOUND);

            Customer customer = customerOptional.get();
            if (!(customer.getPayToDayId().contains(input.planId()) || customer.getStandardId().contains(input.planId()))) {
                return new OutputPort.Error(ErrorMessages.PLAN_ID_NOT_FOUND_IN_CUSTOMER_PLANS);
            }

            PaymentRequest paymentRequest = new PaymentRequest(input.customerId(), input.planId(), input.paymentType(), input.value(), input.description());
            try {
                ResponsePaymentSolverApi processPayment = this.paymentSolverApi.processPayment(paymentRequest);
                if (processPayment == null) {
                    return new OutputPort.Error(ErrorMessages.ERROR_PROCESSING_PAYMENT);
                }

                this.activateCustomer(customer);

                Map<String, Object> paymentData = this.getPaymentRequestMap(input, customer);
                this.messagingSolverApi.sendMessage(new MessagingRequest(customer.getId(), customer.getContact().getEmail(), "PAYMENT", paymentData));

                return new OutputPort.Ok(processPayment.response());
            } catch (Exception e) {
                logger.error("Error processing payment", e);
                return new OutputPort.Error(ErrorMessages.ERROR_PROCESSING_PAYMENT);
            }
        } catch (Exception e) {
            logger.error("Error creating payment", e);
            return new OutputPort.Error(ErrorMessages.ERROR_CREATING_PAYMENT);
        }
    }

    private void activateCustomer(Customer customer) {
        customer.setIsActive(true);
        this.customerRepository.save(customer);
    }

    private OutputPort isValidInput(InputPort inputPort) {
        if (inputPort.customerId() == null) return new OutputPort.Error(ErrorMessages.CUSTOMER_ID_REQUIRED);
        if (inputPort.planId() == null) return new OutputPort.Error(ErrorMessages.PLAN_ID_REQUIRED);
        if (inputPort.paymentType() == null) return new OutputPort.Error(ErrorMessages.PAYMENT_TYPE_REQUIRED);
        if (inputPort.value() == null) return new OutputPort.Error(ErrorMessages.VALUE_REQUIRED);

        return null;
    }

    private Map<String, Object> getPaymentRequestMap(InputPort inputPort, Customer customer) {
        Map<String, Object> paymentRequestMap = new HashMap<>();
        paymentRequestMap.put("customerName", customer.getName());
        paymentRequestMap.put("paymentType", inputPort.paymentType());
        paymentRequestMap.put("value", inputPort.value());
        paymentRequestMap.put("description", inputPort.description());
        return paymentRequestMap;
    }
}
