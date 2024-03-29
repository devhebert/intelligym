package br.com.intelligym.dto.customer;

import br.com.intelligym.model.customer.Address;
import br.com.intelligym.model.customer.Contact;
import br.com.intelligym.model.enums.Plan;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String cpf,
        Address address,
        Contact contact,
        LocalDate dateOfBirth,
        Plan plan,
        List<UUID> payToDayId,
        List<UUID> standardId,
        Boolean isActive
) {
}
