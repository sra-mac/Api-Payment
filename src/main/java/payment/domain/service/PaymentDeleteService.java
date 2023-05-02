package payment.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import payment.domain.exception.BusinessException;
import payment.domain.model.PaymentStatus;
import payment.domain.repository.PaymentRepository;

@AllArgsConstructor
@Service
public class PaymentDeleteService {

	private PaymentRepository paymentRepository;
	
	@Transactional
	public void deletePayment(Long paymentId, PaymentStatus status) {
		if (!status.equals(PaymentStatus.PENDENTE)) {
			throw new BusinessException("Não pode excluir este pagamento, pois consta como processado com "+status );
		}
		paymentRepository.deleteById(paymentId);
	}
}
