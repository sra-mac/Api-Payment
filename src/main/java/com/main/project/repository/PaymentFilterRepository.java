package com.main.project.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.main.project.model.Payment;
import com.main.project.model.PaymentStatus;

import jakarta.persistence.EntityManager;

@Repository
public class PaymentFilterRepository {
	private final EntityManager em;

	public PaymentFilterRepository(EntityManager em) {
		this.em = em;
	}

	public String processPayment(Payment payment) {
		payment.setStatus(PaymentStatus.PENDENTE);

		String payment_method_result = payment.getPayment_method();
		Number card_number_result = payment.getCard_number();


		if((payment_method_result.equals("cartao_credito")) || (payment_method_result.equals("cartao_debito"))) {
			if(card_number_result == null) {
				return null;
			}else {
				return "Success";
			}
		}else {		
			return "Success";
		}
	}
	
	public Payment savePayment(Payment payment) {
		payment.setStatus(PaymentStatus.PENDENTE);
	
		return payment;
	}


	public Integer deletePayment(Integer cod_debit) {
		String sql = "SELECT P FROM Payment AS P Where cod_debit = :cod_debit ";
		
		try {
			var query = em.createQuery(sql, Payment.class);
			if (cod_debit != null) {
				query.setParameter("cod_debit", cod_debit);
			}

			PaymentStatus status_result = query.getResultList().get(0).getStatus();

			if(String.valueOf(status_result).equals("PENDENTE")) {
				Long id_result = query.getResultList().get(0).getId();
				return id_result.intValue();
			}else {
				return 0;
			}

		}catch (IndexOutOfBoundsException ex) {
		    // Caso não houver o código de débito no banco.
			return -1;
		 }
	}

	public List<Payment> filter(Integer cod_debit, String payer, String status) {
		String sql = "SELECT P FROM Payment AS P";
		String conditional = " Where ";

		if (cod_debit != null) {
			sql += conditional + "P.cod_debit = :cod_debit";
			conditional = " and ";
		}

		if (payer != null) {
			sql += conditional + "P.payer like :cpf";
			// conditional = "and";
		}

		if (status != null) {
			sql += conditional + "P.status like :status";
			// conditional = "and";
		}

		var query = em.createQuery(sql, Payment.class);
		if (cod_debit != null) {
			query.setParameter("cod_debit", cod_debit);
		}

		if (payer != null) {
			query.setParameter("payer", payer);
			// conditional = "and";
		}

		if (status != null) {
			query.setParameter("status", status);
			// conditional = "and";
		}
		return query.getResultList();
	}

}
