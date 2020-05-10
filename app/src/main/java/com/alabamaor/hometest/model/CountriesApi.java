package com.alabamaor.hometest.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CountriesApi {


    @GET("rest/v2/all?fields=name;nativeName;area;borders")
    Single<List<CountryModel>> getCountries();


    @GET()
    Single<List<CountryModel>> getCountriesByUrl(@Url String url);


}