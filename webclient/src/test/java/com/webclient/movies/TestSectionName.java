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
import java.util.concurrent.atomic.AtomicInteger;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveMoviesServiceDoc;
import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static org.junit.platform.commons.util.StringUtils.isBlank;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public class TestSectionName {

    /**
     * Procedure 1: Test if the sectionName is null
     * If the sectionName == null, then the test FAILS
     * If the sectionName != null then the test PASSES
     * <p>
     * -> In Procedure 1, we are passing the json doc and a json field to getJsonStream()
     * -> Then we are applying flatMap() operator to go into the menuSections in the json file
     * -> And we are filtering to check the sectionName is null or not
     * -> findAny() helps to find sectionName = null
     * -> And finally returns the boolean value true or false by the terminal operator: isPresent();
     */
    @Test
    @DisplayName("Find Menu SectionName Is Null Procedure 1")
    public void findMenuSectionNameIsNullProcedure1() throws FileNotFoundException {
        boolean areAllActorsNullFound = JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), "payload.movies")
                .flatMap(menuID -> JsonWorkflow.getJsonStream(menuID, "cast"))
                .filter(this::areAllActorsNull)
                .findAny()
                .isPresent();
        
        if (areAllActorsNullFound) {
            Assertions.fail();
        }
    }
    
    /**
     * Procedure 2: Test if the sectionName is null
     * If the sectionName == null, then the test FAILS
     * If the sectionName != null then the test PASSES
     * <p>
     * -> In Procedure 2, we are passing the json doc and a json field to getJsonStream()
     * -> Then we are applying forEach() operator to iterate each and every menuSections
     * -> If menuSections = null then peek() operator prints out menuSections = null
     * -> Then we are using the terminal operator: count() to get total count of menuSections which are null
     */
    @Test
    @DisplayName("Find Menu SectionName Is Null Procedure 2")
    public void findMenuSectionNameIsNullProcedure2() throws FileNotFoundException {
        
        AtomicBoolean areActorsNull = new AtomicBoolean(false);

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(menuID -> {
                    
                    long actorsCount = JsonWorkflow.getJsonStream(menuID, "cast")
                            .filter(this::areAllActorsNull)
                            .peek(menuSection -> log.info("All actors are null for title: {}", JsonWorkflow.getJsonString(menuID, ConstantsWorkflow.TITLE)))
                            .count();
                    
                    if (actorsCount > 0) {
                        areActorsNull.set(true);
                    }
                });
        
        if (areActorsNull.get()) {
            Assertions.fail();
        }
    }
    
    @Step("check all actors are null and return a boolean expression")
    private boolean areAllActorsNull(JsonElement menuSection) {
        return JsonWorkflow.isFieldUndefined(menuSection, "actor1") ||
                JsonWorkflow.getJsonString(menuSection, "actor1") == null &&
                        JsonWorkflow.isFieldUndefined(menuSection, "actor2") ||
                JsonWorkflow.getJsonString(menuSection, "actor2") == null &&
                        JsonWorkflow.isFieldUndefined(menuSection, "actor3") ||
                JsonWorkflow.getJsonString(menuSection, "actor3") == null;
    }
    
    /**
     * The Test case validates the 'fileReference' is null/empty in dispatcher
     * -> If the 'media' field is missing, the test case logs for which venueCode it is missing and Fails
     * -> If the 'fileReference' is null/empty, the test case logs for which venueCode it is missing and Fails
     */
    @Test
    @DisplayName("Test FileReference Is Null Or Empty")
    public void testFileReferenceIsNullOrEmpty() throws FileNotFoundException {
        
        AtomicInteger invalidFileReferenceCount = new AtomicInteger();

        JsonWorkflow.getJsonStream(retrieveMoviesServiceDoc(), ConstantsWorkflow.MOVIES)
                .forEach(venueArray -> {
                    
                    String venueCode = JsonWorkflow.getJsonString(venueArray, EMPTY_STRING);
                    
                    try {
                        JsonWorkflow.getJsonStream(venueArray, EMPTY_STRING)
                                .forEach(media -> {
                                    if (isFileReferenceNullInDispatcher(media)) {
                                        log.info("fileReference is null/empty for venueCode: {} in dispatcher", venueCode);
                                        invalidFileReferenceCount.incrementAndGet();
                                    }
                                });
                    } catch (Exception e) {
                        log.error("Exception: {}" + e);
                        log.error("media field is missing for venueCode: {} in dispatcher", venueCode);
                    }
                });
        
        if (invalidFileReferenceCount.get() > 0) {
            Assertions.fail();
        }
    }
    
    @Step("Returns true if the 'fileReference' is null/empty, otherwise returns false.")
    private boolean isFileReferenceNullInDispatcher(JsonElement media) {
        return JsonWorkflow.isFieldUndefined(media, EMPTY_STRING) ||
                isBlank(JsonWorkflow.getJsonString(media, EMPTY_STRING));
    }
}
