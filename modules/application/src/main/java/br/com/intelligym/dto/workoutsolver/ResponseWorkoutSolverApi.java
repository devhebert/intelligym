package br.com.intelligym.dto.workoutsolver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ResponseWorkoutSolverApi(@JsonProperty("id")UUID response) {
}
