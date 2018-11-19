package net.zero.cloud.common.web;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Slf4j
@ControllerAdvice
public class FaultBarrier {

	private static final ImmutableMap<Class<? extends Throwable>, String> EXCEPTION_MAPPINGS;
	static {
		final ImmutableMap.Builder<Class<? extends Throwable>, String> builder = ImmutableMap.builder();
		// SpringMVC中参数类型转换异常，常见于String找不到对应的ENUM而抛出的异常

		//builder.put(UnsatisfiedServletRequestParameterException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		//builder.put(IllegalArgumentException.class, ResponseCode.FIELD_ERROR.getCode().toString());

		// HTTP Request Method不存在

		builder.put(NoHandlerFoundException.class, ResponseCode.INVALID_REQUEST.getCode().toString());
		builder.put(MethodArgumentNotValidException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		builder.put(HttpRequestMethodNotSupportedException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		builder.put(MissingServletRequestParameterException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		builder.put(MissingPathVariableException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		builder.put(MethodArgumentTypeMismatchException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		// 要求有RequestBody的地方却传入了NULL
		builder.put(HttpMessageNotReadableException.class, ResponseCode.FIELD_ERROR.getCode().toString());
		// 其他未被发现的异常
		builder.put(Exception.class, ResponseCode.FIELD_ERROR.getCode().toString());
		EXCEPTION_MAPPINGS = builder.build();
	}

	@ExceptionHandler(AppException.class)
	@ResponseBody
	public ResponseDto exp(HttpServletRequest request, AppException ex) {
		String url = request.getServletPath();
		if (request.getQueryString() != null) {
			url += "?" + request.getQueryString();
		}
		log.warn("url={} 请求错误-->{}:{}",url,ex.getErrCode(),ex.getMessage());
		ResponseDto responseDto = new ResponseDto(ex.getErrCode(),ex.getMessage());
		return responseDto;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseDto noHandlerexp(HttpServletRequest request, Exception ex) {

		String url = "";
		url += request.getServletPath();

		if (request.getQueryString() != null) {

			url += "?" + request.getQueryString();

		}

		Locale locale = LocaleContextHolder.getLocale();

		String code = EXCEPTION_MAPPINGS.get(ex.getClass());
		if (null != code) {

			return new ResponseDto(Integer.valueOf(code));
		}
		log.error("发现没有处理的异常url:{},错误信息:{}", url, ex.getMessage());
		log.error("stack", ex);

		return new ResponseDto(ResponseCode.SERVER_EXCEPTION.getCode());

	}

}
