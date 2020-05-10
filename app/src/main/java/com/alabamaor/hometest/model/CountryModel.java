package com.alabamaor.hometest.model;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CountryModel {

    @SerializedName("nativeName")
    String nativeName;

    @SerializedName("name")
    String name;

    @SerializedName("area")
    double area;

    @SerializedName("borders")
    List<String> borders;


    public CountryModel() {
        this.borders = new ArrayList<>();
    }

    public CountryModel(String nativeName, String name, double area, List<String> borders, String alpha3code) {
        this.nativeName = nativeName;
        this.name = name;
        this.area = area;
        this.borders = new ArrayList<>();
        this.borders = borders;
    }


    public String getNativeName() {
        return nativeName;
    }

    public CountryModel setNativeName(String nativeName) {
        this.nativeName = nativeName;
        return this;
    }

    public String getName() {
        return name;
    }

    public CountryModel setName(String name) {
        this.name = name;
        return this;
    }

    public double getArea() {
        return area;
    }

    public CountryModel setArea(double area) {
        this.area = area;
        return this;
    }

    public List<String> getBorders() {
        return borders;
    }

    public CountryModel setBorders(List<String> borders) {
        this.borders = borders;
        return this;
    }


}
