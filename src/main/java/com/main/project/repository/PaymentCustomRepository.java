package com.main.project.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.main.project.model.Payment;

import jakarta.persistence.EntityManager;

@Repository
public class PaymentCustomRepository {
	private final EntityManager em;

	public PaymentCustomRepository(EntityManager em) {
		this.em = em;
	}
	public List<Payment> deleteTeste (Integer id){

		System.out.println("Aqui: "+ id);
		return null;
	}

	public List<Payment> filter(Integer cod_debito, String cpf, String status){
		String sql = "SELECT P FROM Payment AS P";
		String conditional = " Where ";
		System.out.println("Aqui: "+ cod_debito);
		if(cod_debito != null) {
			sql += conditional + "P.cod_debito = :cod_debito";
			conditional = " and ";
		}

		if(cpf != null) {
			sql += conditional + "P.tp_pessoa like :cpf";
			//conditional = "and";
		}

		if(status != null) {
			sql += conditional + "P.status like :status";
			//conditional = "and";
		}

		var q = em.createQuery(sql, Payment.class);
		if(cod_debito != null) {
			q.setParameter("cod_debito", cod_debito);
		}

		if(cpf != null) {
			q.setParameter("cpf", cpf);
			//conditional = "and";
		}

		if(status != null) {
			q.setParameter("status", status);
			//conditional = "and";
		}
		return q.getResultList();

	}

}
