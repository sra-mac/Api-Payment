package com.main.project.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.project.model.Payment;
import com.main.project.model.PaymentStatus;
import com.main.project.model.PaymentStatusRequest;
import com.main.project.repository.PaymentFilterRepository;
import com.main.project.repository.PaymentRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PaymentController {
	@Autowired
	private final PaymentRepository repository;
	private final PaymentFilterRepository filter_repository;

	@GetMapping("/payment")
	public List<Payment> getAllPayment(){
		return repository.findAll();
	}

	@GetMapping("/payment/{id}")
	public Payment getPaymentById(@PathVariable Long id){
		return repository.findById(id).get();
	}

	@PostMapping("/payment")
	public Payment savePayment(@RequestBody @Valid Payment payment){
		
		var response = filter_repository.processPayment(payment);
		
		if(response.equals("Success")) {
			var save_data = filter_repository.savePayment(payment);
			return repository.save(save_data);
		}else {
			return null;
		}
	}

	@PutMapping("/payment/{id}")
	public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment){
		Payment paymentCurrent = repository.findById(id).get();
		BeanUtils.copyProperties(payment, paymentCurrent, "id");
		return repository.save(paymentCurrent);
	}

	@GetMapping("/filter")
	public List<Payment> filter(@RequestParam(value = "cod_debit", required = false) Integer cod_debit
			, @RequestParam(value = "payer", required = false) String payer
			, @RequestParam(value = "status", required = false) String status){
		return filter_repository.filter(cod_debit, payer, status).stream()
				.map(Payment::converter)
				.collect(Collectors.toList());
	}

	@DeleteMapping("/deletePayment")
	public String deletePayment(@RequestParam(value = "cod_debit", required = false) Integer cod_debit){
		Integer response = filter_repository.deletePayment(cod_debit);

		if(response > 0) {
			repository.deleteById((long)response);
			return "Pagamento excluído com sucesso!";
		}else if(response == 0){
			return "O pagamento não pode ser excluído porque já foi processado com sucesso.";
		}else {
			return "O pagamento não existe.";
		}
	}
	
	
	/*
	 * Código para atualização do status de pagamento, 
	 * por problemas de tipagem, não foi finalizado a tempo
	@RequestMapping(value = "/payments/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Payment> updatePaymentStatus(@PathVariable("id") Long id, @RequestBody PaymentStatusRequest request) throws Exception {
		Payment payment = repository.findById(id).get();

	    if (payment == null) {
	        return ResponseEntity.notFound().build();
	    }

	    PaymentStatus newStatus = request.getNewStatus();

	    if (payment.getStatus() == PaymentStatus.SUCESSO) {
	        // Um pagamento processado com sucesso não pode ter seu status alterado.
	    	throw new Exception("Unchanged status");
	  }

	    if (newStatus == PaymentStatus.SUCESSO) {
	        // Atualizando status para Processado com Sucesso.
	        payment.setStatus(newStatus);
	        repository.save(payment);
	        return ResponseEntity.ok(payment);
	    }

	    if (newStatus == PaymentStatus.FALHA) {
	        // Atualizando status para Processado com Falha.
	        payment.setStatus(newStatus);
	        repository.save(payment);
	        return ResponseEntity.ok(payment);
	    }

	    if (newStatus == PaymentStatus.PENDENTE) {
	        // Atualizando status para Pendente de Processamento.
	        if (payment.getStatus() == PaymentStatus.FALHA) {
	            payment.setStatus(newStatus);
	            repository.save(payment);
	            return ResponseEntity.ok(payment);
	        } else {
	            // Um pagamento que ainda não foi processado não pode voltar para o status Pendente de Processamento.
	        	//return ResponseEntity.badRequest().body("Cannot update status to PENDING for a payment that is not in FAILURE status.");
	        	throw new Exception("Cannot update status to PENDING for a payment that is not in FAILURE status.");
	        }
	    }

	    //return ResponseEntity.badRequest().body("Invalid new status: " + newStatus);
	   return updatePaymentStatus(id, request);
	}*/
}
