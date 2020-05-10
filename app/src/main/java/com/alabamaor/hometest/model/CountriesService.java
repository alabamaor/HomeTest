package com.alabamaor.hometest.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CountriesService {

    private static final String BASE_URL = "https://restcountries.eu/";
    private static final String CODES_URL = "rest/v2/alpha?codes=";
    private static final String SEPARETOR = ";";

    private static CountriesService instance;

    private CountriesApi api = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(CountriesApi.class);


    private CountriesService() {

    }


    public static CountriesService getInstance() {
        if (instance == null)
            instance = new CountriesService();

        return instance;
    }

    public Single<List<CountryModel>> getAllCountries() {
        return api.getCountries();
    }

    public Single<List<CountryModel>> getCountriesByCodes(List<String> codes) {

        String url = BASE_URL + CODES_URL;
        StringBuilder builder = new StringBuilder(url);
        for (String c : codes) {
            builder.append(c + SEPARETOR);
        }
        builder.substring(0, builder.length() - 1);


        return api.getCountriesByUrl(builder.toString());
    }

}
