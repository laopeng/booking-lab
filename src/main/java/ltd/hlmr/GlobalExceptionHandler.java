package ltd.hlmr;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	public String jsonErrorHandler(HttpServletResponse response, RuntimeException e) throws Exception {
		logger.error("接口出错：", e);
		response.setStatus(500);
		String message = e.getMessage();
		return message;
	}

}
