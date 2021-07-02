package com.webclient.countries;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import com.webclient.workflows.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByPopulationDensityServiceDoc;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class TestNullValuesForCountryNamesAndDensity {
    
    private static final int TOTAL_COUNTRIES_WITH_POPULATION_DENSITY_AS_NOT_NULL = 175;
    
    /**
     * 1. Get a list of countries
     * 2. if size of the countriesList = 0 OR countriesList doesn't contain "Cube" then FAIL
     */
    @Test
    @DisplayName("Test Countries List")
    public void testCountriesList() throws FileNotFoundException {
        
        List<String> countriesList =
                JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                        .collect(Collectors.toList());
        
        if (countriesList.size() == 0 || !countriesList.contains("Cuba")) {
            Assertions.fail();
        }
        
    }
    
    /**
     * 1. Get a list of countries that doesn't have density as null
     * 2. Asserts the count with density as not null
     */
    @Test
    @DisplayName("Test Country Names With Population Density As Not Null")
    public void testCountryNamesWithPopulationDensityAsNotNull() throws FileNotFoundException {
        
        List<String> countryNamesWithHeightNotNullList = new ArrayList<>();

        JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                .forEach(country -> {
                    
                    String countryName = JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY);
                    
                    if (!Util.isDensityNull(country)) {
                        countryNamesWithHeightNotNullList.add(countryName);
                    }
                });
        
        assertEquals(countryNamesWithHeightNotNullList.stream().count(), TOTAL_COUNTRIES_WITH_POPULATION_DENSITY_AS_NOT_NULL);
    }
    
}
