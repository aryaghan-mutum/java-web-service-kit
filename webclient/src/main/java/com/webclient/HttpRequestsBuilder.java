package com.webclient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.webclient.BaseResponse.getJsonResponse;
import static com.webclient.BaseResponse.getWebClient;

/**
 * @author Anurag Muthyam
 * url: https://github.com/aryaghan-mutum
 */

public class HttpRequestsBuilder {

    @Description("GET Http method")
    public static JsonElement jsonGet(String url, String appKey) {
        WebClient webClient = getWebClient(url);

        String bodyString = webClient.get()
                .header("AppKey", appKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return getJsonResponse(bodyString);
    }

    @Description("GET Http method")
    public static JsonElement jsonGet(String url, String username, String password) {
        WebClient webClient = getWebClient(url);

        String responseString = webClient.get()
                .headers(headers -> headers.setBasicAuth(username, password))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return getJsonResponse(responseString);
    }

    @Description("Http Delete method")
    public static void httpDELETE(String url, String appKey) {
        WebClient webClient = getWebClient(url);
        webClient.delete()
                .header("AppKey", appKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Description("Http Post method")
    public static JsonElement jsonPOST(String url, JsonElement body, String appKey) {
        Gson gson = new Gson();
        String requestJson = gson.toJson(body);

        return httpPOST(url, appKey, requestJson);
    }

    @Description("Http Post method")
    private static JsonElement httpPOST(String url, String appKey, String requestJson) {

        WebClient webClient = getWebClient(url);

        String bodyString = webClient.post()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("AppKey", appKey)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(Mono.just(requestJson), String.class))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (bodyString != null) {
            JsonParser parser = new JsonParser();

            return parser.parse(bodyString);
        } else {
            return null;
        }
    }

}
