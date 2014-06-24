package uy.edu.ort.arqliv.obligatorio.web.controllers.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import uy.edu.ort.arqliv.obligatorio.dominio.Departure;

/**
 * Clase modelo para poder recibir el form completo en el controller
 * 
 * @author rodrigo
 * 
 */
public class DepartureModel {

	private Long departureId;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyyMMdd")
	private Date departureDate;

	private Long shipId;
	private List<Long> containers = new ArrayList<>();

	private String containersDescriptions;
	private String shipDestination;

	public DepartureModel() {
		super();
	}

	/**
	 * 
	 * @param departure
	 */
	public DepartureModel(Departure departure) {
		this(departure.getId(), 
				departure.getDepartureDate(), 
				departure.getShip() == null ? null : departure.getShip().getId(), 
				departure.getContainersIdList(),
				departure.getContainersDescriptions(), 
				departure.getShipDestination()
			);

	}

	
	/**
	 * 
	 * @param departureId
	 * @param departureDate
	 * @param shipId
	 * @param containers
	 * @param containersDescriptions
	 * @param shipDestination
	 */
	public DepartureModel(Long departureId, Date departureDate, Long shipId, List<Long> containers,
			String containersDescriptions, String shipDestination) {

		this.departureId = departureId;
		this.departureDate = departureDate;
		this.shipId = shipId;
		this.containers = containers;
		this.containersDescriptions = containersDescriptions;
		this.shipDestination = shipDestination;
	}

	public Long getDepartureId() {
		return departureId;
	}

	public void setDepartureId(Long departureId) {
		this.departureId = departureId;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Long getShipId() {
		return shipId;
	}

	public void setShipId(Long shipId) {
		this.shipId = shipId;
	}

	public List<Long> getContainers() {
		return containers;
	}

	public void setContainers(List<Long> containers) {
		this.containers = containers;
	}

	public String getContainersDescriptions() {
		return containersDescriptions;
	}

	public void setContainersDescriptions(String containersDescriptions) {
		this.containersDescriptions = containersDescriptions;
	}

	public String getShipDestination() {
		return shipDestination;
	}

	public void setShipDestination(String shipDestination) {
		this.shipDestination = shipDestination;
	}

	
}