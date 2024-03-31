package br.com.intelligym.client;

import br.com.intelligym.client.workoutsolver.WorkoutRequest;
import br.com.intelligym.client.workoutsolver.WorkoutSolverApi;
import br.com.intelligym.dto.workoutsolver.ResponseWorkoutSolverApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "api-service", url = "${workout-solver.url}")
public interface WorkoutSolverApiImpl extends WorkoutSolverApi {
    @PostMapping("create-random")
    ResponseWorkoutSolverApi getTrainingProtocol(WorkoutRequest workoutRequest);
}
