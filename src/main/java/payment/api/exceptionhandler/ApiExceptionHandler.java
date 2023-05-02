package payment.api.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import payment.domain.exception.BusinessException;
@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleTrade(BusinessException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemReturn problem = new ProblemReturn();
		problem.setStatus(status.value());
		problem.setDatetime(OffsetDateTime.now());
		problem.setMessage(ex.getMessage());

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
}
