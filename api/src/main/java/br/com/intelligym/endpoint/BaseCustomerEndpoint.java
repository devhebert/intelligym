package br.com.intelligym.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "Customer", description = "The Customer API")
@RestController
@RequestMapping("/api/v1/customer")
public abstract class BaseCustomerEndpoint {
}
