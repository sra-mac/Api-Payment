package payment.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payment.domain.model.Payment;
import payment.domain.model.PaymentMethod;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	Optional<Payment> findByIdDebit(Integer idDebit);
	Optional<Payment> findByPaymentMethod(PaymentMethod paymentMethod);
	Optional<Payment> findById(Long id);
}
