package com.main.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.project.model.Payment;
import com.main.project.repository.PaymentFilterRepository;
import com.main.project.repository.PaymentRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PaymentController {
	@Autowired
	private final PaymentRepository repository;
	private final PaymentFilterRepository filterrepository;
	//private final PaymentCustomRepository crepository;

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
		return repository.save(payment);
	}

	@PutMapping("/payment/{id}")
	public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment){
		Payment paymentCurrent = repository.findById(id).get();
		BeanUtils.copyProperties(payment, paymentCurrent, "id");
		return repository.save(paymentCurrent);
	}

	@GetMapping("/filter")
	public List<Payment> filter(@RequestParam(value = "cod_debito", required = false) Integer cod_debito
			, @RequestParam(value = "cpf", required = false) String cpf
			, @RequestParam(value = "status", required = false) String status){
		return filterrepository.filter(cod_debito, cpf, status).stream()
				.map(Payment::converter)
				.collect(Collectors.toList());
	}

	@DeleteMapping("/deletePayment")
	public String deletePayment(@RequestParam(value = "cod_debit", required = false) Integer cod_debit){
		Integer response = filterrepository.deletePayment(cod_debit);
		System.out.println(response);
		if(response > 0) {
			 repository.deleteById((long)response);
			return "Registro excluído com sucesso!";
		}else if(response == 0){
			return "O registro não pôde ser excluído, pois foi finalizado com sucesso.";
		}else {
			return "O registro não existe.";
		}
	}
}
