package br.com.intelligym.configuration;


import br.com.intelligym.client.GymSolverApi;
import br.com.intelligym.client.MessagingSolverApi;
import br.com.intelligym.client.PaymentSolverApi;
import br.com.intelligym.client.WorkoutSolverApi;
import br.com.intelligym.repository.CustomerRepository;
import br.com.intelligym.repository.UserRepository;
import br.com.intelligym.service.authentication.TokenService;
import br.com.intelligym.usecase.authentication.AuthenticationGenerateImpl;
import br.com.intelligym.usecase.authentication.AuthenticationGenerete;
import br.com.intelligym.usecase.customer.*;
import br.com.intelligym.usecase.payment.CreatePayment;
import br.com.intelligym.usecase.payment.CreatePaymentImpl;
import br.com.intelligym.usecase.trainingprotocol.CreateTrainingProtocol;
import br.com.intelligym.usecase.trainingprotocol.CreateTrainingProtocolImpl;
import br.com.intelligym.usecase.user.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class UseCaseConfiguration {

    @RequestScope
    @Bean
    public CreateUser createUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new CreateUserImpl(userRepository, passwordEncoder);
    }

    @RequestScope
    @Bean
    public GetAllUser getAllUser(UserRepository userRepository) {
        return new GetAllUserImpl(userRepository);
    }

    @RequestScope
    @Bean
    public UpdateUser updateUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return new UpdateUserImpl(userRepository, passwordEncoder);
    }

    @RequestScope
    @Bean
    public DeleteUser deleteUser(UserRepository userRepository) {
        return new DeleteUserImpl(userRepository);
    }

    @RequestScope
    @Bean
    public GetUserById getUserById(UserRepository userRepository) {
        return new GetUserByIdImpl(userRepository);
    }


    @RequestScope
    @Bean
    public AuthenticationGenerete authenticationGenerete(AuthenticationManager authenticationManager, TokenService tokenService) {
        return new AuthenticationGenerateImpl(authenticationManager, tokenService);
    }

    @RequestScope
    @Bean
    public CreateCustomer createCustomer(CustomerRepository customerRepository, GymSolverApi myService, MessagingSolverApi messagingSolverApi) {
        return new CreateCustomerImpl(customerRepository, myService, messagingSolverApi);
    }

    @RequestScope
    @Bean
    public GetAllCustomer getAllCustomer(CustomerRepository customerRepository) {
        return new GetAllCustomerImpl(customerRepository);
    }

    @RequestScope
    @Bean
    public CreatePayment createPayment(CustomerRepository customerRepository, PaymentSolverApi paymentSolverApi) {
        return new CreatePaymentImpl(customerRepository, paymentSolverApi);
    }

    @RequestScope
    @Bean
    public CreateTrainingProtocol createTrainingProtocol(CustomerRepository customerRepository, UserRepository userRepository, WorkoutSolverApi workoutSolverApi) {
        return new CreateTrainingProtocolImpl(customerRepository, userRepository, workoutSolverApi);
    }

    @RequestScope
    @Bean
    public UpdateCustomer updateCustomer(CustomerRepository customerRepository, GymSolverApi gymSolverApi) {
        return new UpdateCustomerImpl(customerRepository, gymSolverApi);
    }

    @RequestScope
    @Bean
    public GetCustomerById getCustomer(CustomerRepository customerRepository) {
        return new GetCustomerByIdImpl(customerRepository);
    }

}
