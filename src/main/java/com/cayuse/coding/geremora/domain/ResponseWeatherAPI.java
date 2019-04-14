package com.cayuse.coding.geremora.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ResponseWeatherAPI {
    private String name;
    private String lon;
    private String lat;
    private Double temp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @JsonProperty("coord")
    private void unpackNestedCoord(Map<String,String> coord) {
        this.lon = coord.get("lon");
        this.lat = coord.get("lat");
    }

    @JsonProperty("main")
    private void unpackNestedMain(Map<String,Double> main) {
        this.temp = main.get("temp");
    }
}
