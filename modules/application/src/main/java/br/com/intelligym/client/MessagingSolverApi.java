package br.com.intelligym.client;

import org.springframework.scheduling.annotation.Async;

public interface MessagingSolverApi {
    @Async
    void sendMessage(MessagingRequest sendMessageRequest);
}
