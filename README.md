## Intro

Create a service using Java Spring Boot (and any additional libraries of your choice) that takes ZIP-code as a parameter, then outputs city name, current temperature, time zone, and general elevation at the location with a user-friendly message. 

For example, “At the location $CITY_NAME, the temperature is $TEMPERATURE, the timezone is $TIMEZONE, and the elevation is $ELEVATION”. 

Include documentation with any necessary build instructions and be prepared to discuss your approach.


Use the Open WeatherMap current weather API to retrieve the current temperature and city name. You will be required to sign up for a free API key.

Use the Google Time Zone API to get the current timezone for a location. You will again need to register a “project” and sign up for a free API key * with Google.

Use the Google Elevation API to retrieve elevation data for a location.

In addition to the required assignment above, write a modern JS-based SPA front-end that allows a user to input a zipcode, then output the response-message to the page, preferably utilizing the tool you created above.

## Using

* `Spring boot` - backend
* `React` - frontend
* `Jackson` - JSON library  
* `Swagger` - api documentation



## Build
First step. Run maven install

```
$ mvn clean install
[...]

```

Note: Creating the jar file and also execute npm install will install npm and node locally and run `npm build`
in the `frontend` directory.  Include frontend build files in spring boot jar

## Start

For start the application. Make sure you quit any running servers, and run the jar file 

```
$ java -jar target/geremora-0.0.1-SNAPSHOT.jar
```

Open your web browser, and navigate to http://localhost:8080. 

## Documentacion (Swagger)

Open your web browser, and navigate to http://localhost:8080/swagger-ui.html. 