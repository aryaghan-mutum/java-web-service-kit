package com.webclient.movies;

import com.google.gson.JsonElement;
import com.webclient.workflows.ConstantsWorkflow;
import com.webclient.workflows.JsonWorkflow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class TestMovieTitleAndActors {
    
    /**
     * 1. Get actor1 and actor2 from movies.service.json
     * 2. Store them in a list
     * 3. Assert
     */
    @Test
    @DisplayName("Test Movie Title And Actors")
    public void testMovieTitleAndActorsProcedure1() throws FileNotFoundException {

        Map<String, List<String>> movieMap = new HashMap<>();

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(movie -> {
                    
                    String movieTitle = JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE);
    
                    String actor1 = getActor(movie, ConstantsWorkflow.ACTOR1);
                    String actor2 = getActor(movie, ConstantsWorkflow.ACTOR2);
    
                    List<String> actors = asList(actor1, actor2);
    
                    movieMap.put(movieTitle, actors);
                });
    
        assertEquals(movieMap.keySet().size(), 5);
        
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("casino"));
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("amadeus"));
        
        movieMap.entrySet().stream().collect(toList());
    }
    
    @Test
    @DisplayName("Test Movie Title And Actors")
    public void testMovieTitleAndActorsProcedure2() throws FileNotFoundException {
    
        Map<String, List<String>> movieMap =
                JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .collect(toMap(
                        movie -> JsonWorkflow.getJsonString(movie, ConstantsWorkflow.TITLE),
                        movie -> asList(getActor(movie, ConstantsWorkflow.ACTOR1), getActor(movie, ConstantsWorkflow.ACTOR2))));
        
        assertEquals(movieMap.keySet().size(), 5);
    
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("casino"));
        movieMap.keySet().stream().anyMatch(movieName -> movieName.equalsIgnoreCase("amadeus"));
    
        movieMap.entrySet().stream().collect(toList());
    
    }
    
    private String getActor(JsonElement movie, String actor12) {
        return JsonWorkflow.getJsonStream(movie, ConstantsWorkflow.CAST)
                .filter(cast -> JsonWorkflow.getJsonString(cast, actor12) != null)
                .map(cast -> JsonWorkflow.getJsonString(cast, actor12))
                .reduce((a, b) -> a + "," + b)
                .get();
    }
    
    
}
