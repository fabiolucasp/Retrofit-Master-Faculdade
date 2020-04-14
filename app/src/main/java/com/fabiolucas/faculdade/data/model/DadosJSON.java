package com.fabiolucas.faculdade.data.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DadosJSON {

    private static final String URL_BASE = "https://jsonplaceholder.typicode.com/";
    private static DadosJSON dadosJSON;
    private Retrofit retrofit;

    private DadosJSON() {
        retrofit = new Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static Retrofit DadosJSON() {
        Retrofit retrofit2 = new Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit2;
    }

    public static synchronized DadosJSON getInstance() {
        if (dadosJSON == null) {

            dadosJSON = new DadosJSON();

        }

        return dadosJSON;
    }

    public Retrofit getRetrofit() {
        return retrofit.create(Retrofit.class);
    }

}
