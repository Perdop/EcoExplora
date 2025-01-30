package com.ecoexplora.Ecoexplora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "extinctAnimals")

public class ExtinctAnimals {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	private String name;
	private Integer animalType;
	private String about;
	private Integer living;
	private String state;
	private String animalPhoto;
	
	public ExtinctAnimals(String name, Integer animalType, String about, Integer living, String state, String animalPhoto) {	
		this.name = name;
		this.animalType = animalType;
		this.about = about;
		this.living = living;
		this.state = state;
		this.animalPhoto = animalPhoto;
	}
	
	public Integer getId() {
		return id;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getAnimalType() {
		return animalType;
	}
	public void setAnimalType(Integer animalType) {
		this.animalType = animalType;
	}

	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}

	public Integer getLiving() {
		return living;
	}
	public void setLiving(Integer living) {
		this.living = living;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getAnimalPhoto() {
		return animalPhoto;
	}
	public void setAnimalPhoto(String animalPhoto) {
		this.animalPhoto = animalPhoto;
	}

	public ExtinctAnimals() {
		
	}
	
}