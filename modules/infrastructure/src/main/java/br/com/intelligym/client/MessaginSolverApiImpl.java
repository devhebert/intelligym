package br.com.intelligym.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-service", url = "${messaging-solver.url}")
public interface MessaginSolverApiImpl extends MessagingSolverApi {
    @PostMapping("send")
    void sendMessage(MessagingRequest sendMessageRequest);
}
