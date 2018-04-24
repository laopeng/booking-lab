package ltd.hlmr;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	public String jsonErrorHandler(HttpServletResponse response, RuntimeException e) throws Exception {
		response.setStatus(500);
		String message = e.getMessage();
		return message;
	}

}
