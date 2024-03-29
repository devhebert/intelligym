package br.com.intelligym.dto.gymsolver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ResponseGymSolverApi(@JsonProperty("id") UUID response){
}