package com.webclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

@Slf4j
public abstract class BaseResponse {

    @Description("Get the webClient object using builder pattern")
    public static WebClient getWebClient(String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Description("Converts the responseString into JsonElement and return the response")
    public static JsonElement getJsonResponse(String responseString) {
        JsonParser parser = new JsonParser();
        JsonElement response = parser.parse(responseString);
        return response;
    }

    @Description("The procedure saves the response in the local when the service is fired")
    public static void saveJSON(String filePath, JsonElement document) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .serializeNulls()
                    .create();
            
            gson.toJson(document, writer);
        }
    }
    
}
