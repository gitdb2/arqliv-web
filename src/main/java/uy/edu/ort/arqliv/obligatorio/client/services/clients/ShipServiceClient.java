package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Date;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.common.ShipService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

/**
 * 
 * @author rodrigo
 *
 */
public class ShipServiceClient {

	private ShipService shipService;

	/**
	 * Spring injection
	 * @param shipService
	 */
	public void setShipService(ShipService shipService) {
		this.shipService = shipService;
	}
	/**
	 * Crea un barco en la DB y retorna su id
	 * @param login 
	 * @param ship
	 * @return
	 * @throws CustomServiceException
	 */
	public Long create(String login, Ship ship) throws CustomServiceException {
		return shipService.store(login, ship);
	}
	/**
	 * lista todos los barcos en el sistema
	 * @param login 
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Ship> list(String login) throws CustomServiceException {
		return shipService.list(login);
	}
	/**
	 * Actualiza la informacion de un barco para una determinada 
	 * fecha (regla de negocio: no se puede modificar la capacidad de 
	 * un barco si no arribo al puerto en essa fecha)
	 * @param login 
	 * @param ship
	 * @param arrivalDate
	 * @return
	 * @throws CustomServiceException
	 */
	public Long update(String login, Ship ship, Date arrivalDate) throws CustomServiceException {
		return shipService.update(login, ship, arrivalDate);
	}

	/**
	 * Retoran un barco por id
	 * @param login 
	 * @param id
	 * @return
	 * @throws CustomServiceException
	 */
	public Ship find(String login, long id) throws CustomServiceException {
		return shipService.find(login, id);
	}

	/**
	 * da de baja un barco por id, si no esta usado en nungun arribo, de lo contrario tira excpcion
	 * @param login 
	 * @param id
	 * @throws CustomServiceException
	 */
	public void delete(String login, long id) throws CustomServiceException {
		shipService.delete(login, id);
	}
}
