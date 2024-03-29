package br.com.intelligym.model.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Plan {
    PAY_TO_DAY("Pay to Day Plan"),
    STANDARD("Standard Plan");

    private final String plan;

    Plan(String plan) {
        this.plan = plan;
    }

    public String getPlan() {
        return plan;
    }

    public int getPlanType() {
        return this.ordinal();
    }
}
