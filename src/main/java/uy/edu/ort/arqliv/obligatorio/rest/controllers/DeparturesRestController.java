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

import uy.edu.ort.arqliv.obligatorio.common.DepartureService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

@RestController
@RequestMapping(value = "/departures")
public class DeparturesRestController {

	private static final Logger logger = LoggerFactory.getLogger(DeparturesRestController.class);

	@Autowired
	DepartureService departureService;


/**
 *  * Crea un departure en el sistema indicandole el id del barco y 
	 * la lista de ids de contenedores.
	 * En caso de error se tira excepcion
 * @param user
 * @param shipId
 * @param arrivalId
 * @param containers
 * @param departure
 * @return
 * @throws CustomServiceException
 */
	@RequestMapping(value = "/create", method = RequestMethod.POST,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long create(
		@RequestParam(value="user", required=true) String user,
		@RequestParam(value="shipId", required=true) Long shipId, 
		@RequestParam(value="arrivalId", required=true) Long arrivalId, 
		@RequestParam(value="containers") List<Long> containers,
		@RequestBody Departure departure) throws CustomServiceException {
		
		
		logger.info("Create : "+ user + "  " + departure.toString() + " "+containers);
		
		return departureService.store(user, departure, shipId, containers, arrivalId);
	}

	
	/**
	 * Listado de todas las partidas del sistema
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Departure> list(@RequestParam(value="user", required=true) String user) {
		logger.info("List: " + user);
		return departureService.list(user);
	}
/**
 * Modifica el departure recibido, se le pasa el id del nuevo barco o 
	 * el mismo id que tenia si no se modifica, y la nueva lista de ids de contenedores,
	 * se deber enviar haya cambiado o no (con los que ya tenia)
	 * En caso de error se tira excepcion
 * @param user
 * @param shipId
 * @param arrivalId
 * @param containers
 * @param departure
 * @return
 * @throws CustomServiceException
 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long update(
			@RequestParam(value="user", required=true) String user,
			@RequestParam(value="shipId", required=true) Long shipId, 
			@RequestParam(value="arrivalId", required=true) Long arrivalId, 
			@RequestParam(value="containers") List<Long> containers,
			@RequestBody Departure departure) throws CustomServiceException {
		
		logger.info("update : "+ user + "  " + departure.toString()+ " "+arrivalId + " "+containers  );
		return departureService.update(user,
				departure, shipId, containers, arrivalId);
	
	}

	
	
	/**
	 * Obtiene un departure por id (carga eager de atributos)
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Departure find(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") long id) throws CustomServiceException {
		logger.info("update : "+ user +  " " + id);

		return departureService.find(user, id);
	}

	/**
	 * elimina un departure por id
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

		departureService.delete(user, id);
	}
	
}
