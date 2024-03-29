package br.com.intelligym.dto.paymentsolver;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ResponsePaymentSolverApi(@JsonProperty("id") UUID response){
}