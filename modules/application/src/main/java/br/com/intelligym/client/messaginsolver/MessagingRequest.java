package br.com.intelligym.client.messaginsolver;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class MessagingRequest implements Serializable {
    private UUID customerId;
    private String email;
    private String type;
    private Map<String, Object> data;

    public MessagingRequest() {
    }


    public MessagingRequest(UUID customerId, String email, String type, Map<String, Object> data) {
        this.customerId = customerId;
        this.email = email;
        this.type = type;
        this.data = data;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
