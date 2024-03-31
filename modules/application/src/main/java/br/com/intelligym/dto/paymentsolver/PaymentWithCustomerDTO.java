package br.com.intelligym.dto.paymentsolver;

import br.com.intelligym.dto.customer.CustomerDTO;

public record PaymentWithCustomerDTO(PaymentDTO payment, CustomerDTO customer) {
}
