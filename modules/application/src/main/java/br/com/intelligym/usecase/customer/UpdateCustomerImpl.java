package br.com.intelligym.usecase.customer;

import br.com.intelligym.client.gymsolver.GymSolverApi;
import br.com.intelligym.client.gymsolver.PlanRequest;
import br.com.intelligym.dto.gymsolver.ResponseGymSolverApi;
import br.com.intelligym.exception.payment.ErrorMessages;
import br.com.intelligym.model.customer.Customer;
import br.com.intelligym.model.enums.Plan;
import br.com.intelligym.repository.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.logging.Logger;

public class UpdateCustomerImpl implements UpdateCustomer {
    private final Logger logger = Logger.getLogger(UpdateCustomerImpl.class.getName());
    private final CustomerRepository customerRepository;
    private final GymSolverApi gymSolverApi;
    private final EnumMap<Plan, BiConsumer<Customer, UUID>> planHandlers;

    public UpdateCustomerImpl(CustomerRepository customerRepository, GymSolverApi gymSolverApi) {
        this.customerRepository = customerRepository;
        this.gymSolverApi = gymSolverApi;

        this.planHandlers = new EnumMap<>(Plan.class);
        this.planHandlers.put(Plan.PAY_TO_DAY, this::handlePayToDay);
        this.planHandlers.put(Plan.STANDARD, this::handleStandard);
    }

    @Transactional
    public OutputPort execute(InputPort inputPort) {
        try {
            return this.customerRepository.findById(inputPort.id())
                    .map(customer -> {
                        OutputPort validationOutput = this.validateInput(inputPort);
                        if (validationOutput != null) return validationOutput;

                        this.updateFields(customer, inputPort);

                        if (!customer.getPlan().equals(inputPort.plan())) {
                            PlanRequest planRequest = new PlanRequest(customer.getId(), inputPort.plan());
                            ResponseGymSolverApi savedPlan = this.gymSolverApi.createPlan(planRequest);

                            if (savedPlan != null) {
                                UUID savedPlanId = savedPlan.response();

                                BiConsumer<Customer, UUID> planHandler = this.planHandlers.get(inputPort.plan());
                                if (planHandler != null) {
                                    planHandler.accept(customer, savedPlanId);
                                }
                            }
                        }

                        Customer updatedCustomer = this.customerRepository.save(customer);

                        return new OutputPort.Ok(updatedCustomer.getId());
                    })
                    .orElse(new OutputPort.Error(ErrorMessages.CUSTOMER_NOT_FOUND));
        } catch (Exception e) {
            logger.severe(e.getMessage());
            return new OutputPort.Error("Error updating customer");
        }
    }

    private void handlePayToDay(Customer customer, UUID savedPlanId) {
        customer.getPayToDayId().add(savedPlanId);
    }

    private void handleStandard(Customer customer, UUID savedPlanId) {
        customer.getStandardId().add(savedPlanId);
    }

    private UpdateCustomer.OutputPort validateInput(InputPort inputPort) {
        if (inputPort.name() != null && inputPort.name().length() < 5) {
            return new UpdateCustomer.OutputPort.Error("O nome deve ter pelo menos 5 caracteres.");
        }

        if (inputPort.cpf() != null && inputPort.cpf().length() < 10) {
            return new UpdateCustomer.OutputPort.Error("Insira um CPF válido.");
        }

        if (inputPort.address() != null) {
            return new UpdateCustomer.OutputPort.Error("Endereço inválido");
        }

        if (inputPort.contact() == null )   {
            return new UpdateCustomer.OutputPort.Error("Contato inválido");
        }

        if (inputPort.dateOfBirth() == null) {
            return new UpdateCustomer.OutputPort.Error("Data de nascimento inválida");
        }

        if (inputPort.plan() == null) {
            return new UpdateCustomer.OutputPort.Error("Plano inválido");
        }

        return null;
    }

    private void updateFields(Customer customer, InputPort inputPort) {
        if (inputPort.name() != null) {
            customer.setName(inputPort.name());
        }

        if (inputPort.cpf() != null) {
            customer.setCpf(inputPort.cpf());
        }

        if (inputPort.address() != null) {
            customer.setAddress(inputPort.address());
        }

        if (inputPort.contact() != null) {
            customer.setContact(inputPort.contact());
        }

        if (inputPort.dateOfBirth() != null) {
            customer.setDateOfBirth(inputPort.dateOfBirth());
        }

        if (inputPort.plan() != null) {
            customer.setPlan(inputPort.plan());
        }
    }
}
