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
		String sql = "SELECT P FROM Payment AS P Where cod_debito = :cod_debito ";
		
		try {
			var query = em.createQuery(sql, Payment.class);
			if (cod_debit != null) {
				query.setParameter("cod_debito", cod_debit);
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
		    // expected
			System.out.println("BBB: "+ ex);
			return -1;
		  }
	}

	public List<Payment> filter(Integer cod_debito, String cpf, String status) {
		String sql = "SELECT P FROM Payment AS P";
		String conditional = " Where ";

		if (cod_debito != null) {
			sql += conditional + "P.cod_debito = :cod_debito";
			conditional = " and ";
		}

		if (cpf != null) {
			sql += conditional + "P.tp_pessoa like :cpf";
			// conditional = "and";
		}

		if (status != null) {
			sql += conditional + "P.status like :status";
			// conditional = "and";
		}

		var q = em.createQuery(sql, Payment.class);
		if (cod_debito != null) {
			q.setParameter("cod_debito", cod_debito);
		}

		if (cpf != null) {
			q.setParameter("cpf", cpf);
			// conditional = "and";
		}

		if (status != null) {
			q.setParameter("status", status);
			// conditional = "and";
		}
		return q.getResultList();

	}

}
