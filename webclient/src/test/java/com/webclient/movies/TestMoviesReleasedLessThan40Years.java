package com.webclient.movies;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import com.webclient.workflows.Util;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public class TestMoviesReleasedLessThan40Years {
    
    /**
     * Approach 1 using forEach() :
     * 1. Gets movieTitle for each movie
     * 2. Gets yearReleased for each movie
     * 3. Calculates the difference between the (currentYear - yearReleased)
     * 4. If yearReleased < 40 then adds the movieTitle to the list
     */
    @Test
    @DisplayName("Test Total Count Of Movies Released Less Than 40 Years From Current Year")
    public void testTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYear() throws FileNotFoundException {
        int countForProcedure1 = getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure1();
        int countForProcedure2 = getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure2();
        
        Assertions.assertEquals(countForProcedure1, countForProcedure2);
        
    }
    
    @Step("Approach 1 using forEach(): Get Total Count Of Movies Released Less Than 40 Years From Current Year Procedure 1")
    public int getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure1() throws FileNotFoundException {
        List<String> moviesReleasedLessThan40YearsFromTodayList = new ArrayList<>();
        
        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
                    
                    int yearReleased = Util.convertStringToLocalDateFormat(JsonWorkflow.getJsonString(movie, ConstantsWorkflow.DATE_RELEASED)).getYear();
                    int yearDifference = Util.getCurrentYear() - yearReleased;
                    
                    if (yearDifference < 40) {
                        log.info("movieTitle: {} was released {} year ago", movieTitle, yearDifference);
                        moviesReleasedLessThan40YearsFromTodayList.add(movieTitle);
                    }
                    
                });
        
        return moviesReleasedLessThan40YearsFromTodayList.size();
    }
    
    @Step("Approach 2 using map(), filter() and count(): Get Total Count Of Movies Released Less Than 40 Years From Current Year Procedure 1")
    public int getTotalCountOfMoviesReleasedLessThan40YearsFromCurrentYearProcedure2() throws FileNotFoundException {
        long moviesReleasedLessThan40YearsFromTodayCount = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .map(movie -> JsonWorkflow.getJsonString(movie, ConstantsWorkflow.DATE_RELEASED))
                .map(movie -> Util.convertStringToLocalDateFormat(movie).getYear())
                .map(movie -> Util.getCurrentYear() - movie)
                .filter(yearDifference -> yearDifference < 40)
                .count();
        
        log.info("There are {} movies released 40 years ago", moviesReleasedLessThan40YearsFromTodayCount);
        
        return (int) moviesReleasedLessThan40YearsFromTodayCount;
    }
    
}
