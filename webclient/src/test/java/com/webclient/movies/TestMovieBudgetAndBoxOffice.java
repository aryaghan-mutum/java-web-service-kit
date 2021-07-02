package com.webclient.movies;

import com.google.gson.JsonElement;
import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static com.webclient.workflows.ConstantsWorkflow.BOX_OFFICE;
import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static org.junit.platform.commons.util.StringUtils.isBlank;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public class TestMovieBudgetAndBoxOffice {

    /**
     * Test budget and boxOffice of the movies
     * -> If budget, and boxOffice are null or empty, it is fine.
     * -> If budget or boxOffice is null or empty, the test case FAILS
     */
    @Test
    @DisplayName("Test Budget And BoxOffice Are Null Or Empty For Movies")
    public void testBudgetAndBoxOfficeAreNullOrEmptyForMovies() throws FileNotFoundException {
        Stream<JsonElement> movies = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES);
        AtomicBoolean isCostFieldNull = new AtomicBoolean(false);

        movies.forEach(movie -> {
            String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
            String budget = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.BUDGET);
            String boxOffice = JsonWorkflow.getJsonString(movie, BOX_OFFICE);

            if (isBlank(budget) || isBlank(boxOffice)) {

                // if budget and boxOffice are all null, then skip.
                if (isBlank(budget) && isBlank(boxOffice)) {
                    isCostFieldNull.set(false);
                }
                // if budget or boxOffice is null or empty, the test case fails.
                else {
                    if (isBlank(budget)) {
                        isCostFieldNull.set(true);
                        log.error("budget: is null or empty for movieTitle: {} ", movieTitle);
                    }
                    if (isBlank(boxOffice)) {
                        isCostFieldNull.set(true);
                        log.error("boxOffice: is null or empty for movieTitle: {}", movieTitle);
                    }
                }
            }
        });

        if (isCostFieldNull.get()) {
            Assertions.fail();
        }
    }

}
