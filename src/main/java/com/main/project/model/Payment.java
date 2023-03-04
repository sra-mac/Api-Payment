package com.main.project.model;


import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@NotNull
	Integer cod_debit;
	@NotBlank
	//@CPF
	String payer;
	@NotBlank
	String tp_person;
	@NotNull
	String payment_method;
	Integer card_number;
	@NotNull
	@Min(value = 0)
	Float payment_value;
	
	@Enumerated(EnumType.STRING)
    private PaymentStatus status;

	

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static Payment converter (Payment p) {
		var pay = new Payment();
		pay.setId(p.getId());
		pay.setCod_debit(p.getCod_debit());
		pay.setPayment_method(p.getPayment_method());
		pay.setCard_number(p.getCard_number());
		pay.setPayer(p.getPayer());
		pay.setTp_person(p.getTp_person());
		pay.setPayment_value(p.getPayment_value());
		return pay;
	}
	
}
