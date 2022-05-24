package com.bsifipp.appgeolocalizador.models;

public class UF{
    public int id;
    public String sigla;
    public String nome;
    public Regiao regiao;

    @Override
    public String toString() {
        return nome+" ("+sigla+")";
    }
}

