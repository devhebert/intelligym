package br.com.intelligym.dto.gymsolver;

import br.com.intelligym.model.enums.Plan;

import java.util.UUID;

public record CreatePlanDTO(UUID customerId, Plan plan) {
}
