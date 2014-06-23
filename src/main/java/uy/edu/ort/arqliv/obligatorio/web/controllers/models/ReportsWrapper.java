package uy.edu.ort.arqliv.obligatorio.web.controllers.models;

import java.util.List;

import uy.edu.ort.arqliv.obligatorio.dominio.Arrival;

public class ReportsWrapper {
	private Integer month;
	
	private Long ship;
	
	private List<Arrival> arrivals;

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Long getShip() {
		return ship;
	}

	public void setShip(Long ship) {
		this.ship = ship;
	}

	public List<Arrival> getArrivals() {
		return arrivals;
	}

	public void setArrivals(List<Arrival> arrivals) {
		this.arrivals = arrivals;
	}
}
