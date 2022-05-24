package com.bsifipp.appgeolocalizador.services;

import com.bsifipp.appgeolocalizador.models.UF;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IEstadoService {
    @GET("estados")
    Call<ArrayList<UF>> buscarEstados();
}
