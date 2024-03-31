package br.com.intelligym.client;

import br.com.intelligym.client.gymsolver.GymSolverApi;
import br.com.intelligym.client.gymsolver.PlanRequest;
import br.com.intelligym.dto.gymsolver.ResponseGymSolverApi;
import br.com.intelligym.dto.paymentsolver.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "api-service", url = "${gym-resolver.url}")
public interface GymSolverApiImpl extends GymSolverApi {
    @PostMapping("create")
    ResponseGymSolverApi createPlan(@RequestBody PlanRequest planRequest);
}
