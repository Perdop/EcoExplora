package com.example.home.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class AnimaisExtintosModel implements Parcelable {
    private int id;
    private String name;
    private String about;
    private int animalType;
    private int living;
    private String state;
    private String animalPhoto;

    public AnimaisExtintosModel() {
    }

    public AnimaisExtintosModel(int id, String nome, String sobre, int classe, int existentes, String estado, String img) {
        this.id = id;
        this.name = nome;
        this.about = sobre;
        this.animalType = classe;
        this.living = existentes;
        this.state = estado;
        this.animalPhoto = img;
    }

    // Getters e Setters
    public int getId() {
        return id;
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

    public int getLiving() {
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

    // Parcelable
    protected AnimaisExtintosModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        about = in.readString();
        animalType = in.readInt();
        living = in.readInt();
        state = in.readString();
        animalPhoto = in.readString();
    }

    public static final Creator<AnimaisExtintosModel> CREATOR = new Creator<AnimaisExtintosModel>() {
        @Override
        public AnimaisExtintosModel createFromParcel(Parcel in) {
            return new AnimaisExtintosModel(in);
        }

        @Override
        public AnimaisExtintosModel[] newArray(int size) {
            return new AnimaisExtintosModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(about);
        dest.writeInt(animalType);
        dest.writeInt(living);
        dest.writeString(state);
        dest.writeString(animalPhoto);
    }

}
