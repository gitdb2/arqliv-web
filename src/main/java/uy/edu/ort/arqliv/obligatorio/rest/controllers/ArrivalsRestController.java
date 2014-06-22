package uy.edu.ort.arqliv.obligatorio.rest.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.arqliv.obligatorio.common.ArrivalService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

@RestController
@RequestMapping(value = "/arrivals")
public class ArrivalsRestController {

	private static final Logger logger = LoggerFactory.getLogger(ArrivalsRestController.class);

	@Autowired
	ArrivalService arrivalService;

	@RequestMapping(value = "/test", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long test(
			@RequestParam(value="user", required=true) String user,
			@RequestParam(value="shipId", required=true) Long shipId, 
			@RequestParam(value="c") List<Long> containers)
//			@RequestBody Arrival arrival) 
					throws CustomServiceException {
		
		
		System.out.println(containers);
		
		return 2L;
		
	}
	

	/**
	 * Crea un arrival en el sistema indicandole el id del barco y 
	 * la lista de ids de contenedores. retorna el id Del arrival creado
	 * En caso de error se tira excepcion
	 * @param user
	 * @param shipId
	 * @param arrival
	 * @param containers
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long create(
			@RequestParam(value="user", required=true) String user,
			@RequestParam(value="shipId", required=true) Long shipId, 
			@RequestParam(value="containers") List<Long> containers,
			@RequestBody Arrival arrival) throws CustomServiceException {
		logger.info("Create : "+ user + "  " + arrival.toString() + " "+containers);
		
		return arrivalService.store(user, arrival, shipId, containers);
	}

	
	/**
	 * Listado de todos los arribos en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Arrival> list(@RequestParam(value="user", required=true) String user) {
		logger.info("List: " + user);
		return arrivalService.list(user);
	}
/**
 * Modifica el arrival recibido, se le pasa el id del nuevo barco o 
 * el mismo id que tenia si no se modifica, y la nueva lista de ids de contetenedores,
 * se deber enviar haya cambiado o no (con los que ya tenia)
 * En caso de error se tira excepcion
 * @param user
 * @param shipId
 * @param containers
 * @param arrival
 * @return
 * @throws CustomServiceException
 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long update(
			@RequestParam(value="user", required=true) String user, 
			@RequestParam(value="shipId") Long shipId, 
			@RequestParam(value="containers") List<Long> containers,
			@RequestBody Arrival arrival
			) throws CustomServiceException {
		
		logger.info("update : "+ user + "  " + arrival.toString() + " "+containers);
		return arrivalService.update(user,
				arrival, shipId, containers);
	
	}

	
	
	/**
	 * Obtiene un arrival por id (carga eaguer de atributos)
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Arrival find(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") long id) throws CustomServiceException {
		logger.info("update : "+ user +  " " + id);

		return arrivalService.find(user, id);
	}

	/**
	 * elimina un arrival por id
	 * @param id
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public void delete(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") long id) throws CustomServiceException {
		logger.info("update : "+ user +  " " + id);

		arrivalService.delete(user, id);
	}
	
}
