package com.cayuse.coding.geremora;

import com.cayuse.coding.geremora.domain.ResponseGElevationAPI;
import com.cayuse.coding.geremora.domain.ResponseGTimezoneAPI;
import com.cayuse.coding.geremora.domain.ResponseWeatherAPI;
import com.cayuse.coding.geremora.helpers.ValidationHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;

@RestController
@RequestMapping(path = "/api")
public class ServiceController {

    @Value("${api.weather.url}")
    private String API_WEATHER_URL;

    @Value("${api.weather.key}")
    private String API_WEATHER_KEY;

    @Value("${api.weather.units}")
    private String API_WEATHER_UNITS;

    @Value("${api.timezone.google.url}")
    private String API_TIMEZONE_GOOGLE_URL;

    @Value("${api.elevation.google.url}")
    private String API_ELEVATION_GOOGLE_URL;

    @Value("${api.google.key}")
    private String API_GOOGLE_KEY;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping(path="/", produces = "application/json")
    public String get(@RequestParam String zipcode) {

        if (ValidationHelper.validateZipCode(zipcode)) {

            try {

                UriComponentsBuilder uriBuilderWeather = UriComponentsBuilder
                        .fromUriString(API_WEATHER_URL)
                        .queryParam("zip", zipcode)
                        .queryParam("units", API_WEATHER_UNITS)
                        .queryParam("APPID", API_WEATHER_KEY);

                ResponseWeatherAPI resultWeather = restTemplate.getForObject(uriBuilderWeather.toUriString(), ResponseWeatherAPI.class);

                if (resultWeather.getTemp() == null) {
                    throw new Exception();
                }

                UriComponentsBuilder uriBuilderGTimeZone = UriComponentsBuilder
                        .fromUriString(API_TIMEZONE_GOOGLE_URL)
                        .queryParam("location", String.format("%s,%s", resultWeather.getLat(), resultWeather.getLon())).
                                queryParam("timestamp", Instant.now().getEpochSecond()).
                                queryParam("key", API_GOOGLE_KEY);

                ResponseGTimezoneAPI resultGTimeZoneAPI = restTemplate.getForObject(uriBuilderGTimeZone.toUriString(), ResponseGTimezoneAPI.class);

                if (!resultGTimeZoneAPI.getStatus().equals("OK")){
                    throw new Exception();
                }

                UriComponentsBuilder uriBuilderGElevation = UriComponentsBuilder
                        .fromUriString(API_ELEVATION_GOOGLE_URL)
                        .queryParam("locations", String.format("%s,%s", resultWeather.getLat(), resultWeather.getLon())).
                                queryParam("key", API_GOOGLE_KEY);

                ResponseGElevationAPI resultGElevationAPI = restTemplate.getForObject(uriBuilderGElevation.toUriString(), ResponseGElevationAPI.class);

                if (!resultGElevationAPI.getStatus().equals("OK")){
                    throw new Exception();
                }

                return String.format("At the location %s, the temperature is %s, the timezone is %s, and the elevation is %s",
                        resultWeather.getName(), resultWeather.getTemp(), resultGTimeZoneAPI.getTimeZoneName(), resultGElevationAPI.getElevation());

            }  catch (Exception e) {
                return "Server problem / unknown zip code";
            }
        }else{
            return "Zip code invalid";
        }
    }
}
