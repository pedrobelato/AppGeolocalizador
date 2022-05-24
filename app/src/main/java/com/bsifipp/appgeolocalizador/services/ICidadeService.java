package com.bsifipp.appgeolocalizador.services;

import com.bsifipp.appgeolocalizador.models.Cidade;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICidadeService {
    @GET("estados/{uf}/municipios")
    Call<ArrayList<Cidade>> buscarCidade(@Path("uf") int id);
}
