package br.com.intelligym.client;

import br.com.intelligym.dto.gymsolver.ResponseGymSolverApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "api-service", url = "${gym-resolver.url}")
public interface GymSolverApiImpl extends GymSolverApi {
    @PostMapping("create")
    ResponseGymSolverApi getPlan(@RequestBody PlanRequest planRequest);
}
