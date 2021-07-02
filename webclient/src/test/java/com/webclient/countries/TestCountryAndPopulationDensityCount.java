package com.webclient.countries;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import com.webclient.workflows.Util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByPopulationDensityServiceDoc;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class TestCountryAndPopulationDensityCount {
    
    private static final int TOTAL_COUNTRIES_WITH_POPULATION_DENSITY_AS_NULL = 243;
    private static final int TOTAL_COUNTRIES_WITH_POPULATION_DENSITY_AS_NOT_NULL = 175;
    
    /**
     * 1. Gets total count of the countries and performs assertion
     * 2. Asserts the count
     */
    @Test
    @DisplayName("Test Total Countries Count With Density As Null")
    public void testTotalCountriesCountWithDensityAsNull() throws FileNotFoundException {
        int totalCountriesCount = (int) JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES).count();
        assertEquals(totalCountriesCount, TOTAL_COUNTRIES_WITH_POPULATION_DENSITY_AS_NULL);
    }
    
    /**
     * 1. Gets total count of the countries with density as not null, and performs assertion
     * 2. Asserts the count with density as not null
     */
    @Test
    @DisplayName("Test Total Countries Count With Density As Not Null")
    public void testTotalCountriesCountWithDensityAsNotNull() throws FileNotFoundException {
        
        int totalCountriesCountWithDensityAsNotNull =
                (int) JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .filter(country -> !Util.isDensityNull(country))
                        .count();
        
        assertEquals(totalCountriesCountWithDensityAsNotNull, TOTAL_COUNTRIES_WITH_POPULATION_DENSITY_AS_NOT_NULL);
    }
    
}
