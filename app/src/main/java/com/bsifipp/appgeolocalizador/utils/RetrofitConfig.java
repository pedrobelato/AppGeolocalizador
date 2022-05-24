package com.bsifipp.appgeolocalizador.utils;

import com.bsifipp.appgeolocalizador.services.ICidadeService;
import com.bsifipp.appgeolocalizador.services.IEstadoService;
import com.bsifipp.appgeolocalizador.services.IGoogleAPIService;
import com.bsifipp.appgeolocalizador.services.IViaCepService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig(String url){
        retrofit = new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public IEstadoService getEstadoService(){
        return retrofit.create(IEstadoService.class);
    }

    public ICidadeService getCidadeService()
    {
        return retrofit.create(ICidadeService.class);
    }

    public IViaCepService getViaCepService(){
        return retrofit.create(IViaCepService.class);
    }

    public IGoogleAPIService getGoogleApiService()
    {
        return retrofit.create(IGoogleAPIService.class);
    }
}
