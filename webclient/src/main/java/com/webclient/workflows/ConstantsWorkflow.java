package com.webclient.workflows;

import lombok.experimental.UtilityClass;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@UtilityClass
public class ConstantsWorkflow {

    // JSON PATH (Must be changed based on where the project is located)
    public final String BASE_URL_PATH = "/Users/anuragmuthyam/Documents/dev/java-projects/java-web-service-kit/webclient/src/main/resources/json/";
    public final String COUNTRY_BY_CONTINENT = BASE_URL_PATH + "country_by_continent.json";
    public final String COUNTRY_BY_POPULATION_DENSITY = BASE_URL_PATH + "country_by_population_density.json";
    public final String MOVIE_SERVICE_PATH = BASE_URL_PATH + "movies_service.json";

    // MOVIES
    public final String MOVIES = "payload.movies";
    public final String DAYS = "movie.days";
    public final String TITLE = "title";
    public final String YEAR_RELEASED = "yearReleased";
    public final String DATE_RELEASED = "dateReleased";
    public final String LANGUAGE = "movieLanguage.language";
    public final String CAST = "cast";
    public final String DIRECTOR = "director";
    public final String PRODUCER = "producer";
    public final String ACTOR1 = "actor1";
    public final String ACTOR2 = "actor2";
    public final String ACTOR3 = "actor3";
    public final String BUDGET = "cost.budget";
    public final String BOX_OFFICE = "cost.boxOffice";
    public final String MOVIE_RELEASE = "movie.movieRelease";
    public final String COUNTRY_RELEASED = "countryReleased";
    public final String MOVIE_ITEM = "movieItem";
    public final String MOVIE_RELEASED_STATE = "movieReleasedState";
    public final String MOVIE_RELEASED_PRICE = "moveReleasedPrice";
    
    // COUNTRY BY AVG MALE HEIGHT
    public final String COUNTRIES = "payload.countries";
    public final String COUNTRY = "country";
    public final String DENSITY = "density";
    public final String CONTINENT = "continent";

}
