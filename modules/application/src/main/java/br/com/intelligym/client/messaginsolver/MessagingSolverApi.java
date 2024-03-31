package br.com.intelligym.client.messaginsolver;

import br.com.intelligym.client.messaginsolver.MessagingRequest;
import org.springframework.scheduling.annotation.Async;

public interface MessagingSolverApi {
    @Async
    void sendMessage(MessagingRequest sendMessageRequest);
}
