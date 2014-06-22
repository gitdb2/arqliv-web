package uy.edu.ort.arqliv.obligatorio.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.arqliv.obligatorio.common.ReportsService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

@RestController
@RequestMapping(value = "/reports")
public class ReportsRestController {

	private static final Logger logger = LoggerFactory.getLogger(ReportsRestController.class);

	@Autowired
	ReportsService reportsService;


	
	/***
	 * Retorna los arrivals que para un mes (1 Enero)
	 * @param user
	 * @param month
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/arrivalsByMonth/{id}", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Arrival> arrivalsByMonth(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") int month) throws CustomServiceException {
		
		logger.info(user + " , " + month);
		return reportsService.arrivalsByMonth(user, month);
	}
	
	/**
	 * Lista los arrivals filtrando por me y por id barco
	 * @param month
	 * @param shipId
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/arrivalsByMonthByShip/{id}/{shipId}", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Arrival> arrivalsByMonthByShip(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") Integer month, 
			@PathVariable(value="shipId") Long shipId) throws CustomServiceException {
		logger.info(user + " , " + month+ " , " + shipId);
		return reportsService.arrivalsByMonthByShip(user, month, shipId);
	}
	

}
