package com.example.home;

import com.example.home.model.AnimaisExtintosModel;

import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static DataStorage instance;
    private List<AnimaisExtintosModel> animaisList;

    // Construtor privado para evitar instância externa
    private DataStorage() {
        animaisList = new ArrayList<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public List<AnimaisExtintosModel> getAnimaisList() {
        return animaisList;
    }

    public void setAnimaisList(List<AnimaisExtintosModel> lista) {
        animaisList.clear();
        animaisList.addAll(lista);
    }
}
