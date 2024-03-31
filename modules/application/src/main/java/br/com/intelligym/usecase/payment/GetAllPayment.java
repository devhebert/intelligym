package br.com.intelligym.usecase.payment;

import br.com.intelligym.dto.paymentsolver.PaymentWithCustomerDTO;

import java.util.List;

public interface GetAllPayment {
    sealed interface OutputPort permits OutputPort.Error, OutputPort.NoResults, OutputPort.NotAuthorized, OutputPort.Ok {
        public final record Ok(List<PaymentWithCustomerDTO> list) implements OutputPort { }
        public final record NoResults() implements OutputPort { }
        public final record NotAuthorized() implements OutputPort { }
        public final record Error(String message) implements OutputPort { }
    }

    OutputPort execute();
}
