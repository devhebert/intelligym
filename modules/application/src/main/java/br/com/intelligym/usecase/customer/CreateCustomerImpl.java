package br.com.intelligym.usecase.customer;

import br.com.intelligym.client.gymsolver.GymSolverApi;
import br.com.intelligym.client.messaginsolver.MessagingRequest;
import br.com.intelligym.client.messaginsolver.MessagingSolverApi;
import br.com.intelligym.client.gymsolver.PlanRequest;
import br.com.intelligym.dto.gymsolver.ResponseGymSolverApi;
import br.com.intelligym.exception.customer.ErrorMessages;
import br.com.intelligym.model.customer.Address;
import br.com.intelligym.model.customer.Contact;
import br.com.intelligym.model.customer.Customer;
import br.com.intelligym.model.enums.Plan;
import br.com.intelligym.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.UUID;
import java.util.function.BiConsumer;

public class CreateCustomerImpl implements CreateCustomer {
    private final Logger logger = LoggerFactory.getLogger(CreateCustomerImpl.class);
    private final CustomerRepository customerRepository;
    private final GymSolverApi gymSolverApi;
    private final EnumMap<Plan, BiConsumer<Customer, UUID>> planHandlers;
    private final MessagingSolverApi messagingSolverApi;

    public CreateCustomerImpl(CustomerRepository customerRepository, GymSolverApi gymSolverApi, MessagingSolverApi messagingSolverApi) {
        this.customerRepository = customerRepository;
        this.gymSolverApi = gymSolverApi;
        this.messagingSolverApi = messagingSolverApi;

        this.planHandlers = new EnumMap<>(Plan.class);
        this.planHandlers.put(Plan.PAY_TO_DAY, this::handlePayToDay);
        this.planHandlers.put(Plan.STANDARD, this::handleStandard);
    }

    @Transactional
    public OutputPort execute(InputPort inputPort) {
        try {
            if (inputPort == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            OutputPort validateInput = this.isValidInput(inputPort);
            if (validateInput instanceof OutputPort.Error) return validateInput;

            Customer savedCustomer = this.customerRepository.save(new Customer(inputPort.name(), inputPort.cpf(), inputPort.address(), inputPort.contact(), inputPort.dateOfBirth(), inputPort.plan()));

            PlanRequest planRequest = new PlanRequest(savedCustomer.getId(), inputPort.plan());
            UUID savedPlanId = null;
            try {
                Object savedPlan = this.gymSolverApi.createPlan(planRequest);
                if (savedPlan instanceof OutputPort.Error) return (OutputPort.Error) savedPlan;
                if (savedPlan != null) {
                    ResponseGymSolverApi responseGymSolverApi = (ResponseGymSolverApi) savedPlan;
                    savedPlanId = responseGymSolverApi.response();
                }
            } catch (Exception e) {
                logger.error(ErrorMessages.ERROR_CALLING_API, e);
                this.customerRepository.deleteById(savedCustomer.getId());
                return new OutputPort.Error(ErrorMessages.ERROR_CALLING_API);
            }

            BiConsumer<Customer, UUID> planHandler = this.planHandlers.get(inputPort.plan());
            if (planHandler != null) {
                planHandler.accept(savedCustomer, savedPlanId);
                this.customerRepository.save(savedCustomer);
            }

            this.messagingSolverApi.sendMessage(new MessagingRequest(savedCustomer.getId(), inputPort.contact().getEmail(), "NEW_CUSTOMER", null));

            return new OutputPort.Ok(savedCustomer.getId());
        } catch (Exception e) {
            logger.error(ErrorMessages.ERROR_CREATING_CUSTOMER, e);
            return new OutputPort.Error(ErrorMessages.ERROR_CREATING_CUSTOMER);
        }
    }


    private void handlePayToDay(Customer customer, UUID savedPlanId) {
        customer.getPayToDayId().add(savedPlanId);
    }

    private void handleStandard(Customer customer, UUID savedPlanId) {
        customer.getStandardId().add(savedPlanId);
    }

    private OutputPort isValidInput(InputPort inputPort) {
        if (inputPort.name() == null || inputPort.name().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_NAME);
        if (inputPort.cpf() == null || inputPort.cpf().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_CPF);
        if (inputPort.dateOfBirth() == null) return new OutputPort.Error(ErrorMessages.INVALID_DATE_OF_BIRTH);

        OutputPort addressValidation = this.isValidAddress(inputPort.address());
        if (addressValidation instanceof OutputPort.Error) return addressValidation;

        OutputPort contactValidation = this.isValidContact(inputPort.contact());
        if (contactValidation instanceof OutputPort.Error) return contactValidation;

        OutputPort planValidation = this.isValidPlan(inputPort.plan());
        if (planValidation instanceof OutputPort.Error) return planValidation;

        return null;
    }

    private OutputPort isValidAddress(Address address) {
        if (address == null) return new OutputPort.Error(ErrorMessages.INVALID_ADDRESS);
        if (address.getCity() == null || address.getCity().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_CITY);
        if (address.getCountry() == null || address.getCountry().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_COUNTRY);
        if (address.getNumber() == null || address.getNumber().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_NUMBER);
        if (address.getState() == null || address.getState().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_STATE);
        if (address.getStreet() == null || address.getStreet().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_STREET);
        if (address.getZipCode() == null || address.getZipCode().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_ZIP_CODE);

        return null;
    }

    private OutputPort isValidContact(Contact contact) {
        if (contact == null) return new OutputPort.Error(ErrorMessages.INVALID_CONTACT);
        if (contact.getEmail() == null || contact.getEmail().isEmpty()) return new OutputPort.Error(ErrorMessages.INVALID_EMAIL);
        if (contact.getPhoneNumber() == null || contact.getPhoneNumber().isEmpty())
            return new OutputPort.Error(ErrorMessages.INVALID_PHONE);

        return null;
    }

    private OutputPort isValidPlan(Plan plan) {
        if ((plan != Plan.PAY_TO_DAY && plan != Plan.STANDARD) || plan == null)
            return new OutputPort.Error(ErrorMessages.INVALID_PLAN);

        return null;
    }
}