package com.webclient.movies;

import com.google.gson.JsonElement;
import com.webclient.workflows.JsonWorkflow;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static com.webclient.workflows.ConstantsWorkflow.LANGUAGE;
import static com.webclient.workflows.ConstantsWorkflow.MOVIES;
import static com.webclient.workflows.ConstantsWorkflow.TITLE;
import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

/**
 * Test if the language is null
 * If the language == null, then the test FAILS
 * If the language != null then the test PASSES
 */

@Slf4j
public class TestLanguageIsNullForAMovie {
    
    /**
     * Approach 1 using filter(), peek(), findAny() and isPresent():
     * The Test case returns a boolean value. if the value is true then FAILS
     */
    @Test
    @DisplayName("Find language is null Procedure 1")
    public void findLanguageIsNullProcedure1() throws FileNotFoundException {
        boolean isLanguageNullFound = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .filter(this::isLanguageNullForProcedure1)
                .peek(movie -> log.info("language is null for title: {}", JsonWorkflow.getJsonString(movie, TITLE)))
                .findAny()
                .isPresent();
        
        if (isLanguageNullFound) {
            Assertions.fail();
        }
    }
    
    @Step("check language is null for procedure 1")
    private boolean isLanguageNullForProcedure1(JsonElement movie) {
        try {
            return JsonWorkflow.getJsonString(movie, LANGUAGE) == null;
        } catch (Exception ex) {
            log.info("Exception: {}" + ex.getMessage());
            log.info("jsonString method throws exception when the tag is not present, make sure that is also treated as null");
            return true;
        }
    }
    
    /**
     * Approach 2 using filter(), peek(), and count():
     * The Test case returns a total count. if the count > 0 then FAILS
     */
    @Test
    @DisplayName("Find language is null Procedure 2")
    public void findLanguageIsNullProcedure2() throws FileNotFoundException {
        long languageCount = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), MOVIES)
                .filter(this::isLanguageNullForProcedure2)
                .peek(movie -> log.info("language is null for title: {}", JsonWorkflow.getJsonString(movie, TITLE)))
                .count();
        
        if (languageCount > 0) {
            Assertions.fail();
        }
    }
    
    @Step("check language is null for procedure 2")
    private boolean isLanguageNullForProcedure2(JsonElement movie) {
        return JsonWorkflow.isFieldUndefined(movie, LANGUAGE) || JsonWorkflow.getJsonString(movie, LANGUAGE) == null;
    }
    
}
