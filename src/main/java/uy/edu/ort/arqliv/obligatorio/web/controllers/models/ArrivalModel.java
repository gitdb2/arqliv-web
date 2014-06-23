package uy.edu.ort.arqliv.obligatorio.web.controllers.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

/**
 * Clase wrapper para poder recibir el form completo en el controller
 * 
 * @author rodrigo
 * 
 */
public class ArrivalModel {

	private Long arrivalId;
	
	@NotNull
	@DateTimeFormat(pattern = "yyyyMMdd")
	private Date arrivalDate;

	private Long shipId;
	private List<Long> containers = new ArrayList<>();

	private String containersDescriptions;
	private String shipOrigin;

	public ArrivalModel() {
		super();
	}

	public ArrivalModel(Arrival arrival) {
		this(arrival.getId(), 
				arrival.getArrivalDate(), 
				arrival.getShip() == null ? null : arrival.getShip().getId(), 
				arrival.getContainersIdList(),
				arrival.getContainersDescriptions(), 
				arrival.getShipOrigin()
			);

	}

	

	public ArrivalModel(Long arrivalId, Date arrivalDate, Long shipId, List<Long> containers,
			String containersDescriptions, String shipOrigin) {

		this.arrivalId = arrivalId;
		this.arrivalDate = arrivalDate;
		this.shipId = shipId;
		this.containers = containers;
		this.containersDescriptions = containersDescriptions;
		this.shipOrigin = shipOrigin;
	}

	public Long getArrivalId() {
		return arrivalId;
	}

	public void setArrivalId(Long arrivalId) {
		this.arrivalId = arrivalId;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
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

	public String getShipOrigin() {
		return shipOrigin;
	}

	public void setShipOrigin(String shipOrigin) {
		this.shipOrigin = shipOrigin;
	}
	
	public String getContainersString(){
		if(containers == null) 
			return "";
		return StringUtils.join(containers, ",");
	}

}