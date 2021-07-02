package com.webclient.workflows;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Description;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;

import static com.webclient.workflows.JsonPayloadWorkflow.retrieveCountryByPopulationDensityServiceDoc;
import static com.webclient.workflows.JsonWorkflow.getJsonStream;
import static com.webclient.workflows.JsonWorkflow.getJsonString;
import static com.webclient.workflows.JsonWorkflow.isFieldUndefined;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class Util {

    @Description("Convenience shortcut method for logging")
    private static void log(String format, Object... args) {
        System.out.printf(format, args);
        System.out.println("");
    }

    @Description("Returns the current year as a type: integer")
    public static int getCurrentYear() {
        int year = LocalDate.now().getYear();
        return year;
    }

    @Description("1. Parses the String dateReleased variable and extracts year, month and dayOfMont, 2. And converts to LocalDate object")
    public static LocalDate convertStringToLocalDateFormat(String dateReleased) {
        return LocalDate.of(Integer.parseInt(dateReleased.substring(4, 8)),
                Integer.parseInt(dateReleased.substring(0, 2)),
                Integer.parseInt(dateReleased.substring(4, 6)));
    }

    @Description("Check if the density is null")
    public static boolean isDensityNull(JsonElement country) {
        return isFieldUndefined(country, ConstantsWorkflow.DENSITY) || getJsonString(country, ConstantsWorkflow.DENSITY) == null;
    }

    @Description("Check if the continent is null")
    public static boolean isContinentNull(JsonElement country) {
        return isFieldUndefined(country, ConstantsWorkflow.CONTINENT) || getJsonString(country, ConstantsWorkflow.CONTINENT) == null;
    }

    @Description("Gets an array of population densities from a json file")
    public static int[] getCountryDensityArray() throws FileNotFoundException {
        return getJsonStream(retrieveCountryByPopulationDensityServiceDoc(), ConstantsWorkflow.COUNTRIES)
                .filter(country -> !isDensityNull(country))
                .map(country -> getJsonString(country, ConstantsWorkflow.DENSITY))
                .map(Double::valueOf)
                .mapToInt(Double::intValue)
                .toArray();
    }

    @Description("Reads Json file from the project structure.")
    public static JsonElement parseJsonFileFromProjectStructure() throws FileNotFoundException {
        return new JsonParser().parse(new FileReader(
                "/Users/yourUserName/Documents/dev/yourProjectName/search_shorex.json"));
    }

}
