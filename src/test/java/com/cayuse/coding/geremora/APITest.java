package com.cayuse.coding.geremora;

import com.cayuse.coding.geremora.config.RestTemplateResponseErrorHandler;
import com.cayuse.coding.geremora.domain.ResponseGElevationAPI;
import com.cayuse.coding.geremora.domain.ResponseGTimezoneAPI;
import com.cayuse.coding.geremora.domain.ResponseWeatherAPI;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Instant;


public class APITest {

    public static final String API_ELEVATION_GOOGLE_URL = "https://maps.googleapis.com/maps/api/elevation/json";
    public static final String API_TIMEZONE_GOOGLE_URL = "https://maps.googleapis.com/maps/api/timezone/json";
    public static final String API_GOOGLE_KEY = "AIzaSyAQjzRDm37-j07nQfoKwHpeHJxjSUgml_o";

    public static final String API_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    public static final String API_WEATHER_KEY = "96f7f4c93edafff42961299e6b8302e0";

    private RestTemplate restTemplate  = new RestTemplate();



    @Test
    public void getElevation()
    {

        UriComponentsBuilder uriBuilderGElevation = UriComponentsBuilder
                .fromUriString(API_ELEVATION_GOOGLE_URL)
                .queryParam("locations", String.format("%s,%s", "39.7391536" , "-104.9847034")).
                        queryParam("key", API_GOOGLE_KEY);


        ResponseGElevationAPI resultGElevationAPI = restTemplate.getForObject(uriBuilderGElevation.toUriString(), ResponseGElevationAPI.class);
        Assert.assertEquals(resultGElevationAPI.getStatus(), "OK");
        Assert.assertNotNull(resultGElevationAPI.getElevation());

    }

    @Test
    public void getTimezone()
    {

        UriComponentsBuilder uriBuilderGTimeZone = UriComponentsBuilder
                .fromUriString(API_TIMEZONE_GOOGLE_URL)
                .queryParam("location", String.format("%s,%s", "39.6034810" , "-119.6822510")).
                        queryParam("timestamp", Instant.now().getEpochSecond()).
                        queryParam("key", API_GOOGLE_KEY);

        ResponseGTimezoneAPI result = restTemplate.getForObject(uriBuilderGTimeZone.toUriString(), ResponseGTimezoneAPI.class);
        Assert.assertEquals(result.getStatus(), "OK");
        Assert.assertNotNull(result.getTimeZoneName());
        Assert.assertEquals(result.getTimeZoneId(), "America/Los_Angeles");

    }

    @Test
    public void getTimezoneInvalidKey()
    {

        UriComponentsBuilder uriBuilderGTimeZone = UriComponentsBuilder
                .fromUriString(API_TIMEZONE_GOOGLE_URL)
                .queryParam("location", String.format("%s,%s", "39.6034810" , "-119.6822510")).
                        queryParam("timestamp", Instant.now().getEpochSecond()).
                        queryParam("key", "INVALID");

        ResponseGTimezoneAPI result = restTemplate.getForObject(uriBuilderGTimeZone.toUriString(), ResponseGTimezoneAPI.class);
        Assert.assertNotEquals(result.getStatus(), "OK");
    }

    @Test
    public void getElevationInvalid()
    {

        UriComponentsBuilder uriBuilderGElevation = UriComponentsBuilder
                .fromUriString(API_ELEVATION_GOOGLE_URL)
                .queryParam("locations", String.format("%s,%s", "39.7391536" , "-104.9847034")).
                        queryParam("key", "INVALID");

        ResponseGElevationAPI resultGElevationAPI = restTemplate.getForObject(uriBuilderGElevation.toUriString(), ResponseGElevationAPI.class);

        Assert.assertNotEquals(resultGElevationAPI.getStatus(), "OK");

    }

    @Test
    public void getCallWeather()
    {
        UriComponentsBuilder uriBuilderWeather = UriComponentsBuilder
                .fromUriString(API_WEATHER_URL)
                .queryParam("zip", 33101)
                .queryParam("units", "imperial")
                .queryParam("APPID", API_WEATHER_KEY);

        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

        ResponseWeatherAPI result = restTemplate.getForObject(uriBuilderWeather.toUriString(), ResponseWeatherAPI.class);

        Assert.assertNotNull(result.getTemp());

    }

    @Test
    public void getCallWeatherInvalidadKey()
    {
        UriComponentsBuilder uriBuilderWeather = UriComponentsBuilder
                .fromUriString(API_WEATHER_URL)
                .queryParam("zip", 33101)
                .queryParam("units", "imperial")
                .queryParam("APPID", "INVALIDAD");

        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());

        ResponseWeatherAPI result = restTemplate.getForObject(uriBuilderWeather.toUriString(), ResponseWeatherAPI.class);

        Assert.assertNull(result.getTemp());

    }
}
