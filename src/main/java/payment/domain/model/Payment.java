package payment.domain.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import payment.domain.model.Payment;

@Entity
@Data
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer idDebit;
	
	private String payer;
	
	private PaymentMethod paymentMethod;
	
	private Integer cardNumber;
	
	private BigDecimal paymentValue;
	
    private PaymentStatus status;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static Payment converter (Payment p) {
		var pay = new Payment();
		pay.setId(p.getId());
		pay.setIdDebit(p.getIdDebit());
		pay.setPaymentMethod(p.getPaymentMethod());
		pay.setCardNumber(p.getCardNumber());
		pay.setPayer(p.getPayer());
		pay.setPaymentValue(p.getPaymentValue());
		pay.setStatus(p.getStatus());
		return pay;
	}
	
}
