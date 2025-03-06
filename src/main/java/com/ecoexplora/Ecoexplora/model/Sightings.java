package com.ecoexplora.Ecoexplora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sightings")

public class Sightings {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String user;
	private String location;
	private String date;
	private String photo;
	private String animal;
	
	public Sightings(String user, String location, String date, String photo, String animal) {	
		this.user = user;
		this.location = location;
		this.date = date;
		this.photo = photo;
		this.animal = animal;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getAnimal() {
		return animal;
	}
	public void setAnimal(String animal) {
		this.animal = animal;
	}
	


	public Sightings() {
		
	}
	
}
