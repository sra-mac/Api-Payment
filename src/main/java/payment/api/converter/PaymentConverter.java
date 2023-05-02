package payment.api.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import payment.api.model.PaymentModel;
import payment.api.model.input.PaymentInput;
import payment.domain.model.Payment;

@AllArgsConstructor
@Component
public class PaymentConverter {
	private ModelMapper modelMapper;
	
	public PaymentModel toModel(Payment payment) {
		return modelMapper.map(payment, PaymentModel.class);
	}

	public List<PaymentModel> toCollectionModel(List<Payment> payments) {
		// TODO Auto-generated method stub
		return payments.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Payment toEntity(PaymentInput paymentInput) {
		return modelMapper.map(paymentInput, Payment.class);
	}
}
