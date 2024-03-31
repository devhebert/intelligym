package br.com.intelligym.usecase.payment;

import br.com.intelligym.client.paymentsolver.PaymentSolverApi;
import br.com.intelligym.dto.paymentsolver.PaymentDTO;
import org.slf4j.LoggerFactory;


public class GetPaymentByIdImpl implements GetPaymentById {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GetPaymentByIdImpl.class);
    private final PaymentSolverApi paymentSolverApi;

    public GetPaymentByIdImpl(PaymentSolverApi paymentSolverApi) {
        this.paymentSolverApi = paymentSolverApi;
    }

    @Override
    public OutputPort execute(InputPort inputPort) {
        try {
            PaymentDTO payment = this.paymentSolverApi.getPaymentById(inputPort.id());
            if (payment == null) {
                logger.error("Payment not found");
                return new OutputPort.NotFound("Payment not found");
            }

            return new OutputPort.Ok(payment);
        } catch (Exception e) {
            logger.error("Error on get payment by id", e);
            return new OutputPort.Error("Error on get payment by id");
        }
    }
}
