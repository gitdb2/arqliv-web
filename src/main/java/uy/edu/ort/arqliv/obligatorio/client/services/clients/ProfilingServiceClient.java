package uy.edu.ort.arqliv.obligatorio.client.services.clients;

import java.util.Date;
import java.util.List;

import uy.edu.ort.arqliv.obligatorio.common.ProfilingService;
import uy.edu.ort.arqliv.obligatorio.common.exceptions.CustomServiceException;
import uy.edu.ort.arqliv.obligatorio.dominio.Pair;

/**
 * 
 * @author rodrigo
 *
 */
public class ProfilingServiceClient {

	private ProfilingService profilingService;

	public void setProfilingService(ProfilingService profilingService) {
		this.profilingService = profilingService;
	}

	/**
	 * Consulta de tiempo promedio de ejecucion para una fecha determinada
	 * @param login 
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Pair<String, Double>> avgServiceTime(String login, Date forDate) throws CustomServiceException {
		return profilingService.avgServiceTime(login, forDate);
	}
	
	/**
	 *  Consulta de tiempo minimo de ejecucion para una fecha determinada
	 * @param login 
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Pair<String, Long>> minServiceTime(String login, Date forDate) throws CustomServiceException {
		return profilingService.minServiceTime(login, forDate);
	}
	
	/**
	 * Consulta de tiempo maximo de ejecucion para una fecha determinada
	 * @param login 
	 * @param forDate
	 * @return
	 * @throws CustomServiceException
	 */
	public List<Pair<String, Long>> maxServiceTime(String login, Date forDate) throws CustomServiceException {
		return profilingService.maxServiceTime(login, forDate);
	}
	
}
