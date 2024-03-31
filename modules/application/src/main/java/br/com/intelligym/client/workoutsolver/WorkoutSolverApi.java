package br.com.intelligym.client.workoutsolver;

import br.com.intelligym.dto.workoutsolver.ResponseWorkoutSolverApi;

public interface WorkoutSolverApi {
    ResponseWorkoutSolverApi getTrainingProtocol(WorkoutRequest workoutRequest);
}
