package payment.api.model.input;

import java.math.BigDecimal;

import jakarta.validation.Valid;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import payment.domain.model.PaymentMethod;
import payment.domain.model.PaymentStatus;

@Data
public class PaymentInput {
	@Valid
	@NotNull
	private Integer idDebit;
	
	@NotNull
	private String payer;
	
	@NotNull
	private PaymentMethod paymentMethod;
	
	private Integer cardNumber;

	@DecimalMin(value = "0.01")
	@NotNull
	private BigDecimal paymentValue;
	
	@Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
