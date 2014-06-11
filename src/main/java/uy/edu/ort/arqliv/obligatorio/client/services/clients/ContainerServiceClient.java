package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.common.ContainerService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Container;

/**
 * 
 * @author rodrigo
 *
 */
public class ContainerServiceClient {

	private ContainerService containerService;

	/**
	 * setter apra inyeccion de spring
	 * @param containerService
	 */
	public void setcontainerService(ContainerService containerService) {
		this.containerService = containerService;
	}
	/**
	 * Crea un Contenedor en el sistema, se retorna el id
	 * En caso de error se tira excepcion
	 * @param login 
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(String login, Container container) throws CustomServiceException {
		return containerService.store(login, container);
	}
	/**
	 * LIsta todos los contenedores en el sistema
	 * @param login 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Container> list(String login) throws CustomServiceException {
		return containerService.list(login);
	}
	/**
	 * Actualiza la informacion del contenedor (el campo id debe estar cargado)
	 * @param login 
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(String login, Container container) throws CustomServiceException {
		return containerService.update(login, container);
	}

	/**
	 * Se obtiene un contenedor por id
	 * @param login 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Container find(String login, long id) throws CustomServiceException {
		return containerService.find(login, id);
	}

	/**
	 * Elimina un contenedor por id, tira expcion en caso de estar en uso o no poderse realizar la operacion
	 * @param login 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(String login, long id) throws CustomServiceException {
		containerService.delete(login, id);
	}
}
