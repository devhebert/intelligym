package br.com.intelligym.endpoint;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = "*")
@Tag(name = "Training Protocol", description = "Training Protocol API")
@RestController
@RequestMapping("/api/v1/training-protocol")
public abstract class BaseTrainingProtocolEndpoint {
}
