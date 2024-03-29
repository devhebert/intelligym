package br.com.intelligym;

import br.com.intelligym.authentication.SecurityConfigurations;
import br.com.intelligym.configuration.ServiceConfiguration;
import br.com.intelligym.configuration.UseCaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
@EnableAsync
@Import({UseCaseConfiguration.class, ServiceConfiguration.class, SecurityConfigurations.class})
public class IntelliGymApplication {
    public static void main(String[] args) {
        SpringApplication.run(IntelliGymApplication.class, args);
    }
}