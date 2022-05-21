package com.bsifipp.appgeolocalizador.services;

import com.bsifipp.appgeolocalizador.models.GoogleApiModel;
import com.bsifipp.appgeolocalizador.models.ViaCep;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGoogleAPIService {
    @GET("json")
    Call<GoogleApiModel> BuscarPorCEP(@Query("address") String cep, @Query("key") String chave);
}
