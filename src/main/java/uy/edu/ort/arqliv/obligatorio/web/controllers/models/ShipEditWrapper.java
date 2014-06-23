package uy.edu.ort.arqliv.obligatorio.web.controllers.models;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import uy.edu.ort.arqliv.obligatorio.dominio.Ship;

public class ShipEditWrapper {
	private Ship ship;
	
	@NotNull
	@NotEmpty
	private String arrivalDate;
	
	public ShipEditWrapper() {
		super();
	}

	public Ship getShip() {
		return ship;
	}
	
	public void setShip(Ship ship) {
		this.ship = ship;
	}
	
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
}
