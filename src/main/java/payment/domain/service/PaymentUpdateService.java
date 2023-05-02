package payment.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import payment.domain.exception.BusinessException;
import payment.domain.model.Payment;
import payment.domain.model.PaymentStatus;
import payment.domain.repository.PaymentRepository;

@AllArgsConstructor
@Service
public class PaymentUpdateService {
	private PaymentRepository paymentRepository;
	private PaymentSearchService paymentService;
	
	@Transactional
	public Payment updatePayment(Payment payment) {
		// TODO Auto-generated method stub
		Payment pay = paymentService.searchPayment(payment.getId());
		payment.setIdDebit(pay.getIdDebit());
		payment.setPayer(pay.getPayer());
		payment.setPaymentMethod(pay.getPaymentMethod());
		payment.setPaymentValue(pay.getPaymentValue());
		
		checkUpdateStatus(payment.getId(),payment.getStatus());
		return paymentRepository.save(payment);
	}
	
	
	public void checkUpdateStatus(Long idPayment ,PaymentStatus status) {
		Payment pay = paymentService.searchPayment(idPayment);
		//Se status do dado for sucesso, não atualizar...
		if(PaymentStatus.SUCESSO.equals(pay.getStatus())) {
			throw new BusinessException("Pagamento não pode ser atualizado, pois consta como processado com sucesso.");
		}
		//Se o status enviado é igual ao que está cadastrado, não atualizar...
		if(status.equals(pay.getStatus())) {
			throw new BusinessException("Pagamento já consta com o status: "+ status);
		}
		//se status for falha, atualizar somente para pendente
		if(PaymentStatus.FALHA.equals(pay.getStatus()) && !status.equals(PaymentStatus.PENDENTE)) {
			throw new BusinessException("O pagamento processado com falha pode retornar apenas ao status de Pendente. Tente novamente.");
		}

		pay.setStatus(status);
	}
}
