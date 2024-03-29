package br.com.intelligym.client;


import br.com.intelligym.dto.gymsolver.ResponseGymSolverApi;

public interface GymSolverApi {
    ResponseGymSolverApi getPlan(PlanRequest planRequest);
}