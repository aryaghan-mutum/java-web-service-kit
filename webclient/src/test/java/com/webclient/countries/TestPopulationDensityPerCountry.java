package com.webclient.countries;

import com.google.gson.JsonElement;
import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import com.webclient.workflows.Util;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Stream;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByContinentServiceDoc;
import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByPopulationDensityServiceDoc;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class TestPopulationDensityPerCountry {
    
    private static final double LOWEST_POPULATION_DENSITY = 0.0;
    private static final double HIGHEST_POPULATION_DENSITY = 661.96;
    private static final double AVG_POPULATION_DENSITY = 119.67;
    
    /**
     * 1. Gets a stream of countries and remove the ones that has density = null
     * 2. Gets density one after another
     * 3. Gets the min density using min()
     * 4. Assert
     */
    @Test
    @DisplayName("Test Lowest Population Density")
    public void testLowestPopulationDensity() throws FileNotFoundException {
        
        double lowestPopulationDensity = JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                .filter(country -> !Util.isDensityNull(country))
                .map(country -> JsonWorkflow.getJsonDouble(country, ConstantsWorkflow.DENSITY))
                .mapToDouble(Double::doubleValue)
                .min()
                .getAsDouble();
        
        assertEquals(lowestPopulationDensity, LOWEST_POPULATION_DENSITY);
    }
    
    /**
     * 1. Gets a stream of countries and remove the ones that has density = null
     * 2. Gets density one after another
     * 3. Gets the max density using reduce()
     * 4. Assert
     */
    @Test
    @DisplayName("Test Highest Population Density")
    public void testHighestPopulationDensity() throws FileNotFoundException {
        
        OptionalDouble highestPopulationDensity = JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                .filter(country -> !Util.isDensityNull(country))
                .map(country -> JsonWorkflow.getJsonDouble(country, ConstantsWorkflow.DENSITY))
                .mapToDouble(Double::doubleValue)
                .reduce((Double::max));  //can also be written as: (a, b) -> (a >= b) ? a : b
        
        assertEquals(highestPopulationDensity.getAsDouble(), HIGHEST_POPULATION_DENSITY);
    }
    
    /**
     * 1. Gets a stream of countries and remove the ones that has density = null
     * 2. Gets density one after another
     * 3. Gets the avg density using average()()
     * 4. Assert
     */
    @Test
    @DisplayName("Test Average Population Density For All Countries")
    public void testAveragePopulationDensityForAllCountries() throws FileNotFoundException {
        
        DecimalFormat dateFormatter = new DecimalFormat("#.###");
        
        double avgPopulationDensity = Double
                .parseDouble(dateFormatter.format(JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .filter(country -> !Util.isDensityNull(country))
                        .map(country -> JsonWorkflow.getJsonDouble(country, ConstantsWorkflow.DENSITY))
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .getAsDouble()));
        
        assertEquals(avgPopulationDensity, AVG_POPULATION_DENSITY);
    }
    
    /**
     * Test Lowest and Highest population Density from country_by_population_density.json
     */
    @Test
    @DisplayName("Test Lowest And Highest Population Density")
    public void testLowestAndHighestPopulationDensity() throws FileNotFoundException {
        
        // Approach 1:
        testLowestAndHighestPopulationDensityProcedure1();
        
        // Approach 2:
        testLowestAndHighestPopulationDensityProcedure2();
    }
    
    /**
     * Approach 1 using List, forEach() and Map
     * 1. Gets a stream of countries and remove the ones that has density = null
     * 2. Collect a list of country Json object in a list
     * 3. Iterate the countriesList and gets countryName and density
     * 4. Store countryName as a Key and density as a value in a Map
     * 5. Gets the shortestHeight and  tallestHeight from Collections Api
     * 6. Assert
     */
    @Step("Test Lowest And Highest Population Density Procedure 1")
    public static void testLowestAndHighestPopulationDensityProcedure1() throws FileNotFoundException {
        
        Stream<JsonElement> countries = JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES);
        
        List<JsonElement> countriesList = countries
                .filter(country -> !Util.isDensityNull(country))
                .collect(toList());
        
        Map<String, Double> countryNameAndDensityMap = new HashMap<>();
        
        countriesList
                .stream()
                .forEach(country -> countryNameAndDensityMap.put(
                        JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY),
                        JsonWorkflow.getJsonDouble(country, ConstantsWorkflow.DENSITY)));
        
        Double lowestPopulationDensity = Collections.min(countryNameAndDensityMap.values());
        Double highestPopulationDensity = Collections.max(countryNameAndDensityMap.values());
        
        assertEquals(lowestPopulationDensity, LOWEST_POPULATION_DENSITY);
        assertEquals(highestPopulationDensity, HIGHEST_POPULATION_DENSITY);
    }
    
    /**
     * Approach 2 using Collectors.toMap()
     * 1. Gets a stream of countries and remove the ones that has density = null
     * 2. Store countryName as a Key and density as a value in a toMap()
     * 3. Gets the shortestHeight and  tallestHeight from Collections Api
     * 4. Assert
     */
    @Step("Test Lowest And Highest Population Density Procedure 2")
    public static void testLowestAndHighestPopulationDensityProcedure2() throws FileNotFoundException {
        
        Map<String, Double> countryNameAndDensityMap =
                JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .filter(country -> !Util.isDensityNull(country))
                        .collect(toMap(
                                country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY),
                                density -> JsonWorkflow.getJsonDouble(density, ConstantsWorkflow.DENSITY)));
        
        assertEquals(Collections.min(countryNameAndDensityMap.values()), LOWEST_POPULATION_DENSITY);
        assertEquals(Collections.max(countryNameAndDensityMap.values()), HIGHEST_POPULATION_DENSITY);
    }
    
    /**
     * Approach 1:
     * 1. Gets the count from country_by_continent.json
     * 2. Gets the count from country_population_density.json
     * 3. Asserts
     */
    @Test
    @DisplayName("Test Total Count For Country By Continent And Country By Density Procedure 1")
    public void testTotalCountForCountryByContinentAndCountryByDensityProcedure1() throws FileNotFoundException {
        long countriesByContinentCount = JsonWorkflow.getJsonStream(retrieveCountryByContinentServiceDoc(), ConstantsWorkflow.COUNTRIES).count();
        long countriesByPopulationDensityCount  = JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES).count();
        Assertions.assertEquals(countriesByContinentCount, countriesByPopulationDensityCount);
    }
    
    /**
     * Approach 2:
     * 1. Gets the count from country_by_continent.json
     * 2. Gets the count from country_population_density.json
     * 3. Asserts
     */
    @Test
    @DisplayName("func")
    public void testCountFomTwoJsonFiles() throws FileNotFoundException {
        long countriesByContinentWithCount = JsonWorkflow.getJsonStream(retrieveCountryByContinentServiceDoc(), ConstantsWorkflow.COUNTRIES).count();
        long countriesByPopulationDensityCount  = JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES).count();
        
        if (countriesByContinentWithCount != countriesByPopulationDensityCount) {
            Assertions.fail();
        }
    }
    
    
}
