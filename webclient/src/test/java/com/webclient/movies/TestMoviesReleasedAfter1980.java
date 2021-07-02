package com.webclient.movies;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import com.webclient.workflows.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.webclient.workflows.ConstantsWorkflow.DATE_RELEASED;
import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static java.util.stream.Collectors.toList;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class TestMoviesReleasedAfter1980 {
    
    /**
     * Operations used: foreach():
     * 1. Gets movieTitle for each movie
     * 2. Gets yearReleased for each movie
     * 3. If yearReleased > 1980 then add the movieTitle to a list
     * 4. If movieTitle doesn't contain "Amadeus" then FAIL
     */
    @Test
    @DisplayName("Get List Of Movies Released After 1980")
    public void getListOfMoviesReleasedAfter1980() throws FileNotFoundException {
        
        List<String> moviesReleasedAfter1980List = new ArrayList<>();

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
                    
                    int yearReleased = Util.convertStringToLocalDateFormat(JsonWorkflow.getJsonString(movie, DATE_RELEASED)).getYear();
                    
                    if (yearReleased > 1980) {
                        moviesReleasedAfter1980List.add(movieTitle);
                    }
                    
                });
        
        if (!moviesReleasedAfter1980List.contains("Amadeus")) {
            Assertions.fail();
        }
    }
    
    /**
     * Operations used: map(), filter() and collect():
     * 1. Gets yearReleased for each movie
     * 3. If yearReleased > 1980 then add the yearReleased to a list
     * 4. If moviesReleasedAfter1980List.size() != 3 then FAIL
     * 5. If yearReleased != 1984 then FAIL
     */
    @Test
    @DisplayName("Get List Of Movies Released Years After 1980")
    public void getListOfMoviesReleasedYearsAfter1980() throws FileNotFoundException {
        
        List<Integer> moviesReleasedAfter1980List = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .map(movie -> JsonWorkflow.getJsonInt(movie, ConstantsWorkflow.YEAR_RELEASED))
                .filter(yearReleased -> yearReleased > 1980)
                .collect(toList());
        
        Assertions.assertEquals(moviesReleasedAfter1980List.size(), 3);
        
        if (!moviesReleasedAfter1980List.contains(1984)) {
            Assertions.fail();
        }
    }
    
}
