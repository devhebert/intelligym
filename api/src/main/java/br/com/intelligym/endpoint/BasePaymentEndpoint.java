package br.com.intelligym.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "Payment", description = "The Payment API")
@RestController
@RequestMapping("/api/v1/payment")
public abstract class BasePaymentEndpoint {
}
