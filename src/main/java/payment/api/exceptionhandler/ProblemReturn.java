package payment.api.exceptionhandler;

import java.time.OffsetDateTime;
import lombok.Data;

@Data
public class ProblemReturn {
	private Integer status;
	private OffsetDateTime datetime;
	private String message;
}
