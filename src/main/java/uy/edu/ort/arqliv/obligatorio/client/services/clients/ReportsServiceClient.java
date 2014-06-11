package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.common.ReportsService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

/**
 * 
 * @author rodrigo
 *
 */
public class ReportsServiceClient {

	private ReportsService reportsService;

	public void setReportsService(ReportsService reportsService) {
		this.reportsService = reportsService;
	}

	/**
	 * Retorna los arrivals que para un mes (1 Enero)
	 * @param login 
	 * @param month
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> arrivalsByMonth(String login, int month) throws CustomServiceException {
		return reportsService.arrivalsByMonth(login, month);
	}
	
	/**
	 * Lista los arrivals filtrando por me y por id barco
	 * @param login 
	 * @param month
	 * @param shipId
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Arrival> arrivalsByMonthByShip(String login, int month, Long shipId) throws CustomServiceException {
		return reportsService.arrivalsByMonthByShip(login, month, shipId);
	}
	
}
