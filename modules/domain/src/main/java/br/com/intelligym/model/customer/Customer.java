package br.com.intelligym.model.customer;

import br.com.intelligym.model.enums.Plan;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Document(collection = "customer")
public class Customer {
    @Id
    private UUID id;
    private String name;
    private String cpf;
    private Address address;
    private Contact contact;
    private LocalDate dateOfBirth;
    private Plan plan;
    private List<UUID> payToDayId = new ArrayList<>();
    private List<UUID> standardId = new ArrayList<>();
    private List<UUID> trainingProtocolId = new ArrayList<>();
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Boolean isActive;

    public Customer() {
    }

    public Customer(String name, String cpf, Address address, Contact contact, LocalDate dateOfBirth, Plan plan) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.contact = contact;
        this.dateOfBirth = dateOfBirth;
        this.plan = plan;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now().plusYears(1);
        this.isActive = false;
    }

    public Customer(String name, String cpf, Address address, Contact contact, LocalDate dateOfBirth, Plan plan, List<UUID> standardId, List<UUID> payToDayId, List<UUID> trainingProtocolId, LocalDate createdAt, LocalDate updatedAt, Boolean isActive) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.contact = contact;
        this.dateOfBirth = dateOfBirth;
        this.plan = plan;
        this.standardId = standardId;
        this.payToDayId = payToDayId;
        this.trainingProtocolId = trainingProtocolId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }
}