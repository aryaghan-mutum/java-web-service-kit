package com.webclient.countries;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.regex.Pattern;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByContinentServiceDoc;
import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByPopulationDensityServiceDoc;
import static com.webclient.workflows.Util.isContinentNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class TestCountryNameStartsWithLetterZ {
    
    /**
     * Operations used: map(), filter() and count()
     * 1. Map the country names
     * 2. If the country name starts with the letter 'Z' then get the total count
     * 3. Assert the count
     */
    @Test
    @DisplayName("Test Countries Total Count That Starts With Letter Z Using Count")
    public void testCountriesTotalCountThatStartsWithLetterZUsingCount() throws FileNotFoundException {
        
        long countryNameCountThatStartsWithLetterZ =
                JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                        .filter(country -> country.startsWith("Z"))
                        .count();
        
        assertEquals(countryNameCountThatStartsWithLetterZ, 2);
    }
    
    /**
     * Operations used: map() and anyMatch()
     * 1. Map the country names
     * 2. Check If the country name starts with the letter 'Z'
     * 3. Assert the boolean
     */
    @Test
    @DisplayName("Test Countries That Starts With Letter Using AnyMatch")
    public void testCountriesThatStartsWithLetterUsingAnyMatch() throws FileNotFoundException {
        boolean isCountryNameStartsWithLetterZ =
                JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                        .anyMatch(flower -> flower.startsWith("Z"));

        assertEquals(isCountryNameStartsWithLetterZ, true);
    }
    
    /**
     * Operations used: map(), filter() and collect()
     * 1. Map the country names
     * 2. If the country name starts with the letter 'Z' then collect the countries in a list
     * 3. Assert the List which has countryName that starts with letter 'Z'
     */
    @Test
    @DisplayName("Test Countries List That Starts With Letter Z Procedure 1")
    public void testCountriesListThatStartsWithLetterZProcedure1() throws FileNotFoundException {
        List<String> countryNameThatStartsWithLetterZList =
                JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                        .filter(country -> country.startsWith("Z"))
                        .collect(toList());
        
        assertEquals(countryNameThatStartsWithLetterZList.toString(), "[Zambia, Zimbabwe]");
    }
    
    /**
     * Using Collectors.joining(", ")):
     * Operations used: filter(), map(), filter(), map() and collect()
     */
    @Test
    @DisplayName("Test Countries List That Starts With Letter Z Procedure 2")
    public void testCountriesListThatStartsWithLetterZProcedure2() throws FileNotFoundException {
        String countriesStartsWithLetterZ =
                JsonWorkflow.getJsonStream(retrieveCountryByContinentServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .filter(country -> !isContinentNull(country))
                        .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                        .filter(country -> country.startsWith("Z"))
                        .map(String::toUpperCase)
                        .collect(joining(", "));
        
        Assertions.assertEquals(countriesStartsWithLetterZ, "zambia, zimbabwe".toUpperCase());
    }
    
    /**
     * Operations used: filter(), map(), filter(), map() and collect():
     * 1. Map the country names
     * 2. If the country name starts with the letter 'Z' then collect the countries in a list
     * 3. Convert the list to a String with a delimiter: ", "
     * 4. Assert the Countries as type String
     * 5. Convert the list to a String with a delimiter: "-"
     * 6. Assert the Countries as type String
     */
    @Test
    @DisplayName("Test Countries List That Starts With Letter Z Procedure 3")
    public void testCountriesListThatStartsWithLetterZProcedure3() throws FileNotFoundException {
        List<String> countriesStartsWithLetterZList =
                JsonWorkflow.getJsonStream(retrieveCountryByContinentServiceDoc(), ConstantsWorkflow.COUNTRIES)
                        .filter(country -> !isContinentNull(country))
                        .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                        .map(String::toUpperCase)
                        .filter(country -> country.startsWith("Z"))
                        .collect(toList());
        
        String countriesStartsWithLetterZWithComma =
                countriesStartsWithLetterZList
                        .stream()
                        .collect(joining(", "));

        Assertions.assertEquals(countriesStartsWithLetterZWithComma, "zambia, zimbabwe".toUpperCase());
        
        String countriesStartsWithLetterZWithHiphen =
                Pattern.compile(", ")
                        .splitAsStream(countriesStartsWithLetterZWithComma)
                        .collect(joining("-"));

        Assertions.assertEquals(countriesStartsWithLetterZWithHiphen, "zambia-zimbabwe".toUpperCase());
    }
    
    /**
     * Operations used: map(), filter(), reduce(), get(), chars(), distinct(), maoToObj(), sorted() and collect()
     */
    @Test
    @DisplayName("Test Countries Start With Letter Z And Do String Manipulation")
    public void testCountriesStartWithLetterZAndDoStringManipulation() throws FileNotFoundException {
        String countriesStartsWithLetterZStr1 = JsonWorkflow.getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                .map(country -> JsonWorkflow.getJsonString(country, ConstantsWorkflow.COUNTRY))
                .filter(country -> country.startsWith("Z"))
                .reduce((a, b) -> a + " and " + b)
                .get()
                .chars()
                .distinct()
                .mapToObj(c -> String.valueOf((char) c))
                .sorted()
                .collect(joining(" "));

        Assertions.assertEquals(countriesStartsWithLetterZStr1, "  Z a b d e i m n w");
    }
    
    
}
