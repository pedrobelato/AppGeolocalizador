package com.bsifipp.appgeolocalizador.services;

import com.bsifipp.appgeolocalizador.models.Cidade;
import com.bsifipp.appgeolocalizador.models.ViaCep;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IViaCepService {
    @GET("{uf}/{cidade}/{logradouro}/json")
    Call<ArrayList<ViaCep>> buscarLogradouros(@Path("uf") String uf, @Path("cidade") String cidade,@Path("logradouro") String logradouro);
}
