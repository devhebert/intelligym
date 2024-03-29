package br.com.intelligym.dto.user;

import br.com.intelligym.model.enums.JobTitle;

import java.util.UUID;

public record UserDTO(UUID id, String username, String role, JobTitle jobTitle) {}
