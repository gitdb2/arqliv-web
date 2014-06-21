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

import uy.edu.ort.arqliv.obligatorio.common.ContainerService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

@RestController
@RequestMapping(value = "/containers")
public class ContainersRestController {

	private static final Logger logger = LoggerFactory.getLogger(ContainersRestController.class);

	@Autowired
	ContainerService containerService;


	/**
	 * Crea un Contenedor en el sistema, se retorna el id
	 * En caso de error se tira excepcion
	 * @param container
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.PUT,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long create(
			@RequestParam(value="user", required=true) String user, 
			@RequestBody Container container) throws CustomServiceException {
		logger.info("Create : "+ user + "  " + container.toStringConsola());
		return containerService.store(user, container);
	}
	
	
	/**
	 * LIsta todos los contenedores en el sistema
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public List<Container> list(@RequestParam(value="user", required=true) String user) {
		logger.info("List: " + user);
		return containerService.list(user);
	}
	
	/**
	 * Actualiza la informacion del contenedor (el campo id debe estar cargado)
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Long update(
			@RequestParam(value="user", required=true) String user, 
			@RequestBody Container container) throws CustomServiceException {
		logger.info("update : "+ user +  " " + container.toStringConsola());

		return containerService.update(user, container);
	}

	/**
	 * Se obtiene un contenedor por id
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	@RequestMapping(value = "/find/{id}", method = RequestMethod.GET,  headers = "Accept=application/json")
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public Container find(
			@RequestParam(value="user", required=true) String user, 
			@PathVariable(value="id") long id) throws CustomServiceException {
		logger.info("update : "+ user +  " " + id);


		return containerService.find(user, id);
	}

	/**
	 * Elimina un contenedor por id, tira expcion en caso de estar en uso o no poderse realizar la operacion
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

		containerService.delete(user, id);
	}
	
}
