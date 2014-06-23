package uy.edu.ort.arqliv.obligatorio.web.controllers.models;

public class Error {
	private String message;
	private String entity;
	public Error(){}
	
	public Error(String message, String entity) {
		super();
		this.message = message;
		this.entity = entity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
	
}
