package br.com.intelligym.configuration;

import br.com.intelligym.repository.UserRepository;
import br.com.intelligym.service.authentication.AuthorizationService;
import br.com.intelligym.service.authentication.SecurityFilterComponent;
import br.com.intelligym.service.authentication.TokenService;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@Configuration
@EntityScan(basePackages = "br.com.intelligym.model")
@EnableMongoRepositories(basePackages = "br.com.intelligym.repository")
public class ServiceConfiguration {

    @Bean
    AuthorizationService authorizationService(UserRepository userRepository) {
        return new AuthorizationService(userRepository);
    }

    @Bean
    TokenService tokenService() {
        return new TokenService();
    }

    @Bean
    SecurityFilterComponent securityFilter(TokenService tokenService, UserRepository userRepository) {
        return new SecurityFilterComponent(tokenService, userRepository);
    }

}

