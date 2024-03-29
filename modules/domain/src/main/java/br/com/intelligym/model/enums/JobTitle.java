package br.com.intelligym.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum JobTitle {
    PERSONAL_TRAINER("Personal Trainer"),
    ADMIN("Admin"),
    MANAGER("Manager"),
    FITNESS_INSTRUCTOR("Fitness Instructor"),
    NUTRITIONIST("Nutritionist"),
    PHYSIOTHERAPIST("Physiotherapist"),
    CLEANING_STAFF("Cleaning Staff"),
    FRONT_DESK("Front Desk");

    private final String description;

    JobTitle(String description) {
        this.description = description;
    }

    public static JobTitle fromString(String jobTitle) {
        for (JobTitle jt : JobTitle.values()) {
            if (jt.name().equalsIgnoreCase(jobTitle)) {
                return jt;
            }
        }
        throw new IllegalArgumentException("No JobTitle with text " + jobTitle + " found");
    }
}