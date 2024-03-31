package br.com.intelligym.usecase.trainingprotocol;

import br.com.intelligym.client.workoutsolver.WorkoutRequest;
import br.com.intelligym.client.workoutsolver.WorkoutSolverApi;
import br.com.intelligym.dto.workoutsolver.ResponseWorkoutSolverApi;
import br.com.intelligym.model.customer.Customer;
import br.com.intelligym.model.user.User;
import br.com.intelligym.repository.CustomerRepository;
import br.com.intelligym.repository.UserRepository;

import java.util.Optional;
import java.util.logging.Logger;

public class CreateTrainingProtocolImpl implements CreateTrainingProtocol {
    Logger logger = Logger.getLogger(CreateTrainingProtocolImpl.class.getName());
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final WorkoutSolverApi workoutSolverApi;

    public CreateTrainingProtocolImpl(CustomerRepository customerRepository, UserRepository userRepository, WorkoutSolverApi workoutSolverApi) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.workoutSolverApi = workoutSolverApi;
    }

    @Override
    public OutputPort execute(InputPort input) {
        try {
            if (input == null) return new OutputPort.Error("Invalid input");

            OutputPort validationOutput = validateInputPort(input);
            if (validationOutput != null) return validationOutput;

            Optional<Customer> customerOptional = this.customerRepository.findById(input.customerId());
            if (customerOptional.isEmpty()) return new OutputPort.Error("Customer not found");

            if (input.userId() != null) {
                Optional<User> userOptional = this.userRepository.findById(input.userId());
                if (userOptional.isEmpty()) return new OutputPort.Error("User not found");
            }

            WorkoutRequest workoutRequest = new WorkoutRequest(input.customerId(), input.userId(), input.trainingType(), input.variant());
            try {
                ResponseWorkoutSolverApi processWorkout = this.workoutSolverApi.getTrainingProtocol(workoutRequest);
                if (processWorkout == null) return new OutputPort.Error("Error processing workout");

                return new OutputPort.Ok(processWorkout.response());
            } catch (Exception e) {
                logger.severe("Error processing workout");
                return new OutputPort.Error("Error processing workout");
            }
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return null;
    }

    private OutputPort validateInputPort(InputPort inputPort) {
        if (inputPort.variant() == null) return new OutputPort.Error("Variant required");
        if (inputPort.trainingType() == null) return new OutputPort.Error("Training type required");
        if (inputPort.customerId() == null) return new OutputPort.Error("Customer id required");

        return null;
    }
}
