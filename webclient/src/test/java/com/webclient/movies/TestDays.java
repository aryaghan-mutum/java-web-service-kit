package com.webclient.movies;

import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public class TestDays {
    
    /**
     * The Test case checks if each 'day' is null empty or missing to its associated 'menuTitle'
     */
    @Test
    @DisplayName("Test Day Is Null Or Empty Or Missing For Movies")
    public void testDayIsNullOrEmptyOrMissingForMovies() throws FileNotFoundException {
        AtomicInteger invalidDayCount = new AtomicInteger();

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
                    boolean isDayFieldMissing = JsonWorkflow.isFieldUndefined(movie, ConstantsWorkflow.DAYS);
                    
                    try {
                        if (!isDayFieldMissing && JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.DAYS).count() == 0) {
                            invalidDayCount.incrementAndGet();
                            log.error("ERROR: day is empty for movieTitle: {}", movieTitle);
                        } else if (isDayFieldMissing) {
                            log.warn("WARN: day field is missing for movieTitle: {}", movieTitle);
                        }
                    } catch (Exception e) {
                        invalidDayCount.incrementAndGet();
                        log.info("ERROR: day is null for movieTitle: {}", movieTitle);
                        log.info("Exception: {}", e);
                    }
                });
        
        if (invalidDayCount.get() > 0) {
            Assertions.fail();
        }
    }
}
