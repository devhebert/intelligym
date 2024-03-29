package br.com.intelligym.client;

import br.com.intelligym.model.enums.Plan;

import java.io.Serializable;
import java.util.UUID;

public class PlanRequest implements Serializable {
    private UUID customerId;
    private Plan plan;

    public PlanRequest() {
    }

    public PlanRequest(UUID customerId, Plan plan) {
        this.customerId = customerId;
        this.plan = plan;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
