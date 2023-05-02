package payment.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import payment.api.converter.PaymentConverter;
import payment.api.model.PaymentModel;
import payment.api.model.input.PaymentInput;
import payment.domain.exception.BusinessException;
import payment.domain.model.Payment;
import payment.domain.model.PaymentStatus;
import payment.domain.repository.PaymentRepository;
import payment.domain.service.PaymentDeleteService;
import payment.domain.service.PaymentFilterRepository;
import payment.domain.service.PaymentRequestService;
import payment.domain.service.PaymentUpdateService;

@AllArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
	private PaymentRepository paymentRepository;
	private PaymentRequestService requestService;
	private PaymentUpdateService updateService;
	private PaymentDeleteService deleteService;
	private PaymentFilterRepository filterRepository;
	private PaymentConverter paymentConverter;
	
	@GetMapping
	public List<PaymentModel> listAllPayments(){
		return paymentConverter.toCollectionModel(paymentRepository.findAll());
	}
	
	@GetMapping("/{paymentId}")
	public ResponseEntity<PaymentModel> searchPayment(@PathVariable Long paymentId) {
		return paymentRepository.findById(paymentId)
				.map(payment-> ResponseEntity.ok(paymentConverter.toModel(payment)))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentModel addPayment(@RequestBody PaymentInput payment) {
		Payment paymentNew = paymentConverter.toEntity(payment);
		Payment payRequestService = requestService.sendPayment(paymentNew);
		return paymentConverter.toModel(payRequestService);
	}
	
	@PutMapping("/{paymentId}")
	public ResponseEntity<PaymentModel> updatePayment(@PathVariable Long paymentId,@Valid @RequestBody PaymentInput payment){
		if(!paymentRepository.existsById(paymentId)) {
			return ResponseEntity.notFound().build();
		}
		Payment paymentNew = paymentConverter.toEntity(payment);
		paymentNew.setId(paymentId);
		paymentNew = updateService.updatePayment(paymentNew);
		return ResponseEntity.ok(paymentConverter.toModel(paymentNew));
	}
	
	@GetMapping("/filter")
	public List<Payment> filterPayment(@RequestParam(value = "idDebit", required = false) Integer idDebit
			, @RequestParam(value = "payer", required = false) String payer
			, @RequestParam(value = "status", required = false) PaymentStatus status){
		if (idDebit == null && payer == null && status == null) {
	        // Throw an exception or return an error message
			throw new BusinessException("Precisa inserir pelo menos um filtro.");
	    }
		return filterRepository.filterPayment(idDebit, payer, status).stream()
				.map(Payment::converter)
				.collect(Collectors.toList());
	}
	@DeleteMapping("/{paymentId}")
	public ResponseEntity<String> deletePayment(@PathVariable Long paymentId){
		if(!paymentRepository.existsById(paymentId)) {
			throw new BusinessException("Pagamento não encontrado.");
		}
		PaymentStatus status = paymentRepository.findById(paymentId).get().getStatus();

		deleteService.deletePayment(paymentId, status);
		return ResponseEntity.ok("Pagamento excluído com sucesso");
	}
}
