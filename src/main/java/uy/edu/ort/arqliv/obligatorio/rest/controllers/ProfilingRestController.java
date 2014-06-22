package uy.edu.ort.arqliv.obligatorio.rest.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.arqliv.obligatorio.common.ProfilingService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;

@RestController
@RequestMapping(value = "/profiling")
public class ProfilingRestController {

	private static final Logger logger = LoggerFactory.getLogger(ProfilingRestController.class);

	@Autowired
	ProfilingService profilingService;


	

	/**
	 * Consulta de tiempo promedio de ejecucion para una fecha determinada
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/avgServiceTime", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Pair<String, Double>> avgServiceTime(
			@RequestParam(value="user", required=true) String user, 
			@DateTimeFormat(pattern="yyyyMMdd") Date forDate) throws CustomServiceException {
		return profilingService.avgServiceTime(user, forDate);
	}
	
	/**
	 *  Consulta de tiempo minimo de ejecucion para una fecha determinada
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/minServiceTime", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Pair<String, Long>> minServiceTime(
			@RequestParam(value="user", required=true) String user, 
			@DateTimeFormat(pattern="yyyyMMdd") Date forDate) throws CustomServiceException {
		return profilingService.minServiceTime(user, forDate);
	}
	
	/**
	 * Consulta de tiempo maximo de ejecucion para una fecha determinada
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/maxServiceTime", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Pair<String, Long>> maxServiceTime(
			@RequestParam(value="user", required=true) String user, 
			@DateTimeFormat(pattern="yyyyMMdd") Date forDate) throws CustomServiceException {
		return profilingService.maxServiceTime(user, forDate);
	}
	


}
