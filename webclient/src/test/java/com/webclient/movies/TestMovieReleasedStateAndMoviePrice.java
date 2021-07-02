package com.webclient.movies;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static org.junit.platform.commons.util.StringUtils.isBlank;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public class TestMovieReleasedStateAndMoviePrice {
    
    /**
     * The Test case validates 'movieReleasedState' & 'moveReleasedPrice' are both null/empty associated to each
     * 'countryReleased' and for each 'movieTitle'
     * If both 'movieReleasedState' & 'moveReleasedPrice' are null/empty, then the test fails
     */
    @Test
    @DisplayName("Test MovieReleasedState And MoveReleasedPrice Null Or Missing")
    public void testMovieReleasedStateAndMoveReleasedPriceNullOrMissing() throws FileNotFoundException {
        
        AtomicBoolean isMovieReleasedStateAndMoveReleasedPriceNullOrMissing = new AtomicBoolean(false);

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);

                    JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.MOVIE_RELEASE)
                            .forEach(movieRelease -> {
                                
                                String countryReleased = JsonWorkflow.getJsonString(movieRelease, ConstantsWorkflow.COUNTRY_RELEASED);

                                JsonWorkflow.getJsonStream(movieRelease, ConstantsWorkflow.MOVIE_ITEM)
                                        .forEach(movieItem -> {
                                            
                                            boolean isMovieReleasedStateFieldMissing = JsonWorkflow.isFieldUndefined(movieItem, ConstantsWorkflow.MOVIE_RELEASED_STATE);
                                            boolean isMoveReleasedPriceFieldMissing = JsonWorkflow.isFieldUndefined(movieItem, ConstantsWorkflow.MOVIE_RELEASED_PRICE);
                                            
                                            if (!isMovieReleasedStateFieldMissing &&
                                                    !isMoveReleasedPriceFieldMissing &&
                                                    isBlank(JsonWorkflow.getJsonString(movieItem, ConstantsWorkflow.MOVIE_RELEASED_STATE)) &&
                                                    isBlank(JsonWorkflow.getJsonString(movieItem, ConstantsWorkflow.MOVIE_RELEASED_PRICE))) {
                                                
                                                isMovieReleasedStateAndMoveReleasedPriceNullOrMissing.set(true);
                                                log.error("movieReleasedState & moveReleasedPrice are both null/empty "
                                                                + "under countryReleased: {} for "
                                                                + "movieTitle: {}",
                                                        countryReleased,
                                                        movieTitle);
                                            } else if (isMovieReleasedStateFieldMissing && isMoveReleasedPriceFieldMissing) {
                                                log.warn("movieReleasedState & moveReleasedPrice field are both "
                                                                + "missing under countryReleased: {} for "
                                                                + "movieTitle: {}",
                                                        countryReleased,
                                                        movieTitle);
                                            }
                                            
                                        });
                            });
                    
                });
        
        if (isMovieReleasedStateAndMoveReleasedPriceNullOrMissing.get()) {
            Assertions.fail();
        }
    }
}
