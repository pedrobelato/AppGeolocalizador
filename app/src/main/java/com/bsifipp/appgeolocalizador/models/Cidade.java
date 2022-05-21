package com.bsifipp.appgeolocalizador.models;

public class Cidade{
    public String id;
    public String nome;
    public Microrregiao microrregiao;
    public RegiaoImediata regiaoImediata;

    @Override
    public String toString() {
        return nome;
    }
}
