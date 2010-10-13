package me.dilan.webservice;

import java.io.Serializable;

import me.dilan.essentials.Functions;

public class Line implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String lineName;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		lineName = Functions.capitalizeFirstLetters(lineName.toLowerCase());
		this.lineName = lineName;
	}
}
