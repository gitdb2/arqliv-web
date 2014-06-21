package uy.edu.ort.arqliv.obligatorio.rest.controllers.ships;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomInUseServiceException;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

@RestController
@RequestMapping(value = "/ships")
public class ShipsRestController {

	private static final Logger logger = LoggerFactory.getLogger(ShipsRestController.class);

	@Autowired
	ShipService shipService;


	/**
	 * Crea un barco en la DB y retorna su id
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.PUT,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long create(@RequestParam(value="user", required=true) String user, @RequestBody Ship ship) throws CustomServiceException {
		logger.info("Create : "+ user + "  " + ship.toStringConsola());
		return shipService.store(user, ship);
	}
	
	

	@RequestMapping(value = "/list", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ship> list(@RequestParam(value="user", required=true) String user) {
		logger.info("List: " + user);
		return shipService.list(user);
	}
	
	/**
	 * Actualiza la informacion de un barco para una determinada 
	 * fecha (regla de negocio: no se puede modificar la capacidad de 
	 * un barco si no arribo al puerto en essa fecha)
	 * @param ship
	 * @param arrivalDate
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long update(
			@RequestParam(value="user", required=true) String user, 
			@RequestBody Ship ship, 
			@RequestParam(value="arrivalDate", required=true) 
			@DateTimeFormat(pattern="yyyyMMdd") Date arrivalDate) throws CustomServiceException {
		logger.info("update : "+ user +  " " + ship.toStringConsola() + " "+ arrivalDate);

		return shipService.update(user, ship, arrivalDate);
	}

	/**
	 * Retoran un barco por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Ship find(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") long id) throws CustomServiceException {
		logger.info("update : "+ user +  " " + id);


		return shipService.find(user, id);
	}

	/**
	 * da de baja un barco por id, si no esta usado en nungun arribo, de lo contrario tira excpcion
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

		shipService.delete(user, id);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/error", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ship> error(Locale locale, HttpSession session) {
		System.out.println(session.getAttribute("test"));
		throw new CustomInUseServiceException("esta en uso");
//		throw new RestServiceException("Forzada", 777);
	}

}
