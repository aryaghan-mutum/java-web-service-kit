package com.webclient.movies;

import com.google.gson.JsonElement;
import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

import static com.webclient.workflows.ConstantsWorkflow.MOVIES;
import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public class TestLeadActorIsNullForAMovie {
    
    /**
     * The Test case checks if each actor1 (lead actor) under cast is null in movies_service.json
     * If they are null, the test case logs a list of actor1 associated to the movie title
     * and fails the test
     */
    @Test
    @DisplayName("Find Actor1 That Has Null")
    public void findActor1ThatHasNull() throws FileNotFoundException {
        Stream<JsonElement> movies = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), MOVIES);
        
        AtomicBoolean isActorNullFound = new AtomicBoolean(false);
        
        movies.forEach(movie -> {
            String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
            long actor1Count = JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.CAST)
                    .filter(cast -> isActorNull(cast, ConstantsWorkflow.ACTOR1))
                    .peek(venue -> log.info("actor1 is null for movieTitle: {}", movieTitle))
                    .count();
            
            if (actor1Count > 0) {
                isActorNullFound.set(true);
            }
        });
        
        if (isActorNullFound.get()) {
            Assertions.fail();
        }
    }
    
    /**
     * The Test case checks if each actor2 under cast is not null in movies_service.json
     * If they are not null, the test case logs a list of actor3 associated to the movie title
     * and fails the test
     */
    @Test
    @DisplayName("Find Actor3 That Has Null")
    public void findActor3ThatHasNotNull() throws FileNotFoundException {
        Stream<JsonElement> movies = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), MOVIES);

        AtomicBoolean isActor3NotNullFound = new AtomicBoolean(false);
        
        movies.forEach(movie -> {
            
            String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
            
            try {
                String actor3 = JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.CAST)
                        .map(cast -> JsonWorkflow.getJsonString(cast, ConstantsWorkflow.ACTOR3))
                        .reduce((a, b) -> a + "," + b)
                        .get();
    
                long actor3Count = JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.CAST)
                        .filter(cast -> !isActorNull(cast, ConstantsWorkflow.ACTOR3))
                        .peek(venue -> log.info("actor3 is {} for movieTitle: {}", actor3, movieTitle))
                        .count();
    
    
                if (actor3Count > 0) {
                    isActor3NotNullFound.set(true);
                }
            }catch (Exception e) {
                log.info("actor3 is null for movieTitle: {}", movieTitle);
            }
            
        });
        
        if (isActor3NotNullFound.get()) {
            Assertions.fail();
        }
    }
    
    @Step("check if actor is null")
    private boolean isActorNull(JsonElement offering, String actor) {
        return JsonWorkflow.isFieldUndefined(offering, actor) || JsonWorkflow.getJsonString(offering, actor) == null;
    }
}
