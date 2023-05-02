package com.api.payment;


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import payment.ApiPaymentApplication;
import payment.domain.model.Payment;
import payment.domain.model.PaymentMethod;
import payment.domain.repository.PaymentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiPaymentApplication.class)
class ApiPaymentApplicationTests {

	@Autowired
    PaymentRepository paymentRepository;
	
    @Test
    public void testAddPaymentMainStream() {
    	Payment payment = new Payment();

    	payment.setIdDebit(1);
        payment.setPayer("01234567891");
        payment.setPaymentMethod(PaymentMethod.PIX);
        payment.setCardNumber(null);
        payment.setPaymentValue(new BigDecimal("100.00"));
        paymentRepository.save(payment);

        Assert.assertNotNull(paymentRepository.findById(1L).get());
    }

}
