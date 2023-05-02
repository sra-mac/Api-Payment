package payment.domain.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import payment.domain.model.Payment;
import payment.domain.model.PaymentStatus;

@Repository
public class PaymentFilterRepository {
	private final EntityManager em;

	public PaymentFilterRepository(EntityManager em) {
		this.em = em;
	}

	public List<Payment> filterPayment(Integer idDebit, String payer, PaymentStatus status) {
		String sql = "SELECT P FROM Payment AS P";
		String conditional = " Where ";

		if (idDebit != null) {
			sql += conditional + "P.idDebit = :idDebit";
			conditional = " and ";
		}

		if (payer != null) {
			sql += conditional + "P.payer like :payer";
		}

		if (status != null) {
			sql += conditional + "P.status like :status";
		}

		var query = em.createQuery(sql, Payment.class);
		if (idDebit != null) {
			query.setParameter("idDebit", idDebit);
		}

		if (payer != null) {
			query.setParameter("payer", payer);
		}

		if (status != null) {
			query.setParameter("status", status);
		}
		
		return query.getResultList();
	}
}
