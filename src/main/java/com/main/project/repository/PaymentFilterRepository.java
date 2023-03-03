package com.main.project.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.main.project.model.Payment;

import jakarta.persistence.EntityManager;

@Repository
public class PaymentFilterRepository {
	private final EntityManager em;

	public PaymentFilterRepository(EntityManager em) {
		this.em = em;
	}

	public Integer deletePayment(Integer cod_debit) {
		String sql = "SELECT P FROM Payment AS P Where cod_debit = :cod_debit ";
		
		try {
			var query = em.createQuery(sql, Payment.class);
			if (cod_debit != null) {
				query.setParameter("cod_debit", cod_debit);
			}
			System.out.println(query.getResultList().get(0).getId());

			String status_result = query.getResultList().get(0).getStatus();
			if(status_result.equals("PENDENTE")) {
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

	public List<Payment> filter(Integer cod_debit, String identification, String status) {
		String sql = "SELECT P FROM Payment AS P";
		String conditional = " Where ";

		if (cod_debit != null) {
			sql += conditional + "P.cod_debit = :cod_debit";
			conditional = " and ";
		}

		if (identification != null) {
			sql += conditional + "P.identification like :cpf";
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

		if (identification != null) {
			query.setParameter("identification", identification);
			// conditional = "and";
		}

		if (status != null) {
			query.setParameter("status", status);
			// conditional = "and";
		}
		return query.getResultList();
	}

}
