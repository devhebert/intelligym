package br.com.intelligym.client.workoutsolver;

import br.com.intelligym.model.enums.TrainingType;
import br.com.intelligym.model.enums.Variant;

import java.io.Serializable;
import java.util.UUID;

public class WorkoutRequest implements Serializable {
    private UUID customerId;
    private UUID userId;
    private TrainingType trainingType;
    private Variant variant;

    public WorkoutRequest() {
    }

    public WorkoutRequest(UUID customerId, UUID userId, TrainingType trainingType, Variant variant) {
        this.customerId = customerId;
        this.userId = userId;
        this.trainingType = trainingType;
        this.variant = variant;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Variant getVariant() {
        return variant;
    }

    public void setVariant(Variant variant) {
        this.variant = variant;
    }
}
