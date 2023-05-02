package payment.api.model;

import java.math.BigDecimal;

import lombok.Data;
import payment.domain.model.PaymentMethod;
import payment.domain.model.PaymentStatus;

@Data
public class PaymentModel {

	private Long id;
	private Integer idDebit;
	private String payer;
	private PaymentMethod paymentMethod;
	private Integer cardNumber;
	private BigDecimal paymentValue;
    private PaymentStatus status;
}
