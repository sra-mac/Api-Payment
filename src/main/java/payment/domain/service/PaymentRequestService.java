package payment.domain.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import payment.domain.service.PaymentRequestService;

import lombok.AllArgsConstructor;
import payment.domain.exception.BusinessException;
import payment.domain.model.Payment;
import payment.domain.model.PaymentMethod;
import payment.domain.model.PaymentStatus;
import payment.domain.repository.PaymentRepository;

@AllArgsConstructor
@Service
public class PaymentRequestService {
	private PaymentRepository paymentRepository;
	
	@Transactional
	public Payment sendPayment(Payment payment) {

		Integer cardNumber = payment.getCardNumber();
		Integer sizeData = payment.getPayer().length();
		PaymentMethod methodPayment = payment.getPaymentMethod();
		
		validPayer(payment, sizeData);
		checkIdDebit(payment);
		checkMethod(methodPayment, cardNumber);
		checkPaymentValue(payment.getPaymentValue());
		
		payment.setStatus(PaymentStatus.PENDENTE);
		
		return paymentRepository.save(payment);
	}
	
	public Boolean checkIdDebit(Payment payment) {
		boolean debitUsed = paymentRepository.findByIdDebit(payment.getIdDebit())
				.stream()
				.anyMatch(paymentExist->!paymentExist.equals(payment));
		
		if(debitUsed) {
			throw new BusinessException("Código de pagamento já cadastrado no sistema. Por favor, escolha um código de pagamento diferente.");
		}
		return debitUsed;
		
	}
	
	public void checkMethod(PaymentMethod methodPayment, Integer cardNumber) {
		if((methodPayment.equals(PaymentMethod.CREDITO) || methodPayment.equals(PaymentMethod.DEBITO)) && cardNumber == null) {
			throw new BusinessException("O número do cartão é obrigatório para os métodos de pagamento crédito e débito.");
		}
	}
	
	public void validPayer(Payment payment, Integer sizeData) {
		if(sizeData!= 11 && sizeData != 14) {
			throw new BusinessException("Por favor, insira um CPF ou CNPJ válido. Certifique-se de que não haja pontuação e de que o número esteja correto.");
		}
		
		if(!payment.getPayer().matches("\\d+")) {
			throw new BusinessException("Por favor, insira apenas números. Certifique-se de que não haja pontuação e de que o número esteja correto.");
		}
	}
	
	public void checkPaymentValue(BigDecimal payValue) {
		if(payValue == null|| payValue.compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("Precisa inserir o valor do pagamento.");
		}

	}
}
