package br.com.intelligym.client.gymsolver;


import br.com.intelligym.dto.gymsolver.ResponseGymSolverApi;
import br.com.intelligym.dto.paymentsolver.PaymentDTO;

import java.util.List;

public interface GymSolverApi {
    ResponseGymSolverApi createPlan(PlanRequest planRequest);
}