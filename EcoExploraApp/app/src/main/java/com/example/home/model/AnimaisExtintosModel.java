package com.example.home.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class AnimaisExtintosModel implements Parcelable {
    private int id;            // ID do animal extinto
    private String nome;       // Nome do animal extinto
    private String sobre;      // Descrição sobre o animal extinto
    private int classe;        // Classe do animal (como mamífero, réptil, etc.)
    private int existentes;    // Número de animais existentes
    private String estado;     // Estado do animal (extinto, em extinção, etc.)
    private String img;

    // Construtor padrão
    public AnimaisExtintosModel() {
    }

    // Construtor com todos os campos
    public AnimaisExtintosModel(int id, String nome, String sobre, int classe, int existentes, String estado, String img) {
        this.id = id;
        this.nome = nome;
        this.sobre = sobre;
        this.classe = classe;
        this.existentes = existentes;
        this.estado = estado;
        this.img = img;
    }

    // Getters e Setters

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public int getClasse() {
        return classe;
    }

    public void setClasse(int classe) {
        this.classe = classe;
    }

    public int getExistentes() {
        return existentes;
    }

    public void setExistentes(int existentes) {
        this.existentes = existentes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    // Implementação do Parcelable

    // Método que cria o objeto a partir do Parcel
    protected AnimaisExtintosModel(Parcel in) {
        id = in.readInt();
        nome = in.readString();
        sobre = in.readString();
        classe = in.readInt();
        existentes = in.readInt();
        estado = in.readString();
        img = in.readString();
        Log.d("Teste", "Img no Parcel: " + img);  // Log para verificar o valor de 'img' no Parcel
    }


    // Método que cria o array de objetos Parcelable
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

    // Métodos obrigatórios da interface Parcelable
    @Override
    public int describeContents() {
        return 0; // Não há objetos especiais a serem descritos
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nome);
        dest.writeString(sobre);
        dest.writeInt(classe);
        dest.writeInt(existentes);
        dest.writeString(estado);
        dest.writeString(img);
    }

}
