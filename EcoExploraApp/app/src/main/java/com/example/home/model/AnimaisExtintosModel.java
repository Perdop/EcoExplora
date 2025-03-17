package com.example.home.model;

public class AnimaisExtintosModel {
    private static AnimaisExtintosModel instance; // Instância única
    private int id;
    private String name;
    private String about;
    private int animalType;
    private Integer living;
    private String state;
    private String animalPhoto;

    private AnimaisExtintosModel() {
    }

    public static AnimaisExtintosModel getInstance() {
        if (instance == null) {
            instance = new AnimaisExtintosModel();
        }
        return instance;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getAnimalType() {
        return animalType;
    }

    public void setAnimalType(int animalType) {
        this.animalType = animalType;
    }

    public Integer getLiving() {
        return living;
    }

    public void setLiving(int living) {
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
}
