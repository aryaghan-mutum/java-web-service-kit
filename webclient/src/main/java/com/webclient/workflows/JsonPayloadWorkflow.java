package com.webclient.workflows;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class JsonPayloadWorkflow {

    public static JsonElement retrieveMoviesServiceDoc() throws FileNotFoundException {
        return retrieveServiceDocument(ConstantsWorkflow.MOVIE_SERVICE_PATH);
    }

    public static JsonElement retrieveCountryByPopulationDensityServiceDoc() throws FileNotFoundException {
        return retrieveServiceDocument(ConstantsWorkflow.COUNTRY_BY_POPULATION_DENSITY);
    }

    public static JsonElement retrieveCountryByContinentServiceDoc() throws FileNotFoundException {
        return retrieveServiceDocument(ConstantsWorkflow.COUNTRY_BY_CONTINENT);
    }

    private static JsonElement retrieveServiceDocument(String servicePath) throws FileNotFoundException {
        FileReader reader = new FileReader(servicePath);
        JsonParser jsonParser = new JsonParser();
        return jsonParser.parse(reader);
    }
}
