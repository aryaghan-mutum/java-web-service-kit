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
public class TestMovieReleasedState {

    /**
     * Procedure 1 using isFieldUndefined():
     * The Test case validates 'movieReleasedState' is null/empty/missing for each 'movieTitle'
     */
    @Test
    @DisplayName("Test Movie ReleasedState is Null Or Missing Procedure 1")
    public void testMovieReleasedStateNullOrMissingProcedure1() throws FileNotFoundException {

        AtomicBoolean isMoveReleasedStateNullOrMissing = new AtomicBoolean(false);

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {

                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);

                    JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.MOVIE_RELEASE)
                            .forEach(movieRelease -> {

                                String countryReleased = JsonWorkflow.getJsonString(movieRelease, ConstantsWorkflow.COUNTRY_RELEASED);

                                JsonWorkflow.getJsonStream(movieRelease, ConstantsWorkflow.MOVIE_ITEM)
                                        .forEach(movieItem -> {

                                            boolean isMenuItemTitleMissing = JsonWorkflow.isFieldUndefined(movieItem, ConstantsWorkflow.MOVIE_RELEASED_STATE);

                                            if (!isMenuItemTitleMissing &&
                                                    isBlank(JsonWorkflow.getJsonString(movieItem, ConstantsWorkflow.MOVIE_RELEASED_STATE))) {

                                                isMoveReleasedStateNullOrMissing.set(true);
                                                log.error("movieReleasedState is null under countryReleased: {} for "
                                                                + "movieTitle: {}",
                                                        countryReleased,
                                                        movieTitle);
                                            }

                                            if (isMenuItemTitleMissing) {
                                                log.warn("movieReleasedState field is missing under countryReleased: {} for "
                                                                + "movieTitle: {}",
                                                        countryReleased,
                                                        movieTitle);
                                            }
                                        });


                            });
                });

        if (isMoveReleasedStateNullOrMissing.get()) {
            Assertions.fail();
        }
    }

    /**
     * Procedure 2 using try and catch block:
     * The Test case validates 'movieReleasedState' is null/missing for each 'movieTitle'
     */
    @Test
    @DisplayName("Test Movie ReleasedState is Null Or Missing Procedure 2")
    public void testMovieReleasedStateNullOrMissingProcedure2() throws FileNotFoundException {

        AtomicBoolean isMoveReleasedStateNullOrMissing = new AtomicBoolean(false);

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {

                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);

                    JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.MOVIE_RELEASE)
                            .forEach(movieRelease -> {

                                String countryReleased = JsonWorkflow.getJsonString(movieRelease, ConstantsWorkflow.COUNTRY_RELEASED);

                                JsonWorkflow.getJsonStream(movieRelease, ConstantsWorkflow.MOVIE_ITEM)
                                        .forEach(movieItem -> {

                                            try {

                                                String movieReleasedState = JsonWorkflow.getJsonString(movieItem, ConstantsWorkflow.MOVIE_RELEASED_STATE);

                                                if (isBlank(movieReleasedState)) {
                                                    isMoveReleasedStateNullOrMissing.set(true);
                                                    log.error("movieReleasedState is null/empty under countryReleased: {} for "
                                                                    + "movieTitle: {}",
                                                            countryReleased,
                                                            movieTitle);
                                                }
                                            } catch (Exception e) {
                                                log.warn("movieReleasedState field is missing under countryReleased: {} for "
                                                                + "movieTitle: {}",
                                                        countryReleased,
                                                        movieTitle);
                                            }

                                        });


                            });
                });

        if (isMoveReleasedStateNullOrMissing.get()) {
            Assertions.fail();
        }
    }

}
