package br.com.intelligym.client;

import br.com.intelligym.dto.trainingprotocol.ResponseWorkoutSolverApi;

public interface WorkoutSolverApi {
    ResponseWorkoutSolverApi getTrainingProtocol(WorkoutRequest workoutRequest);
}
