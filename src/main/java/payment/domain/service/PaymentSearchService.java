package payment.domain.service;


import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import payment.domain.exception.BusinessException;
import payment.domain.model.Payment;
import payment.domain.repository.PaymentRepository;

@AllArgsConstructor
@Service
public class PaymentSearchService {
	private PaymentRepository paymentRepository;
	
	public Payment searchPayment(Long paymentId) {
		return paymentRepository.findById(paymentId)
				.orElseThrow(()-> new BusinessException("Pagamento não encontrado"));
	}
	
}
