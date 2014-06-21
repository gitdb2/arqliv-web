package uy.edu.ort.arqliv.obligatorio.rest.controllers.exceptionMapper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import uy.edu.ort.arqliv.obligatorio.common.exceptions.ErrorInfo;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.RestServiceException;

@Component
@ControllerAdvice
/**
 * Clase que va a convertir las excepciones a json para enviarlas con error 400 en caso de erro y usando el objeto ErrorInfo
 * @author rodrigo
 *
 */
public class RestExceptionProcessor {

	@ExceptionHandler(RestServiceException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo inUse(HttpServletRequest req, RestServiceException ex) {
		String errorMessage = ex.getMessage();
		String errorURL = req.getRequestURL().toString();
		
		return new ErrorInfo(errorMessage, ex.getCode(), errorURL);
	}

}