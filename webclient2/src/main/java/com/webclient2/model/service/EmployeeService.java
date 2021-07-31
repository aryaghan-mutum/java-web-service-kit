package com.webclient2.model.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.webclient2.model.employee.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
public class EmployeeService {

    @Description("GET: get all employee data")
    public Employee getEmployeeUserResponse(String url) {
        WebClient webClient = WebClient.create();
        Employee employeeResponse = webClient.get()
                        .uri(url)
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Employee>() {})
                        .timeout(Duration.ofSeconds(10)).block();
        return employeeResponse;
    }

    @Description("POST: create new record in database")
    public String postEmployee(String url, MultiValueMap<String, String> employeeBodyMap) {
        WebClient webClient = WebClient.create();
        String employeeResponse = webClient.post()
                .uri(url)
                .headers(this::setHeaders)
                .body(BodyInserters.fromFormData(employeeBodyMap))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> ex.getRawStatusCode() == HttpStatus.OK.value() ? Mono.empty() : Mono.error(ex))
                .timeout(Duration.ofSeconds(10))
                .block();

        assert employeeResponse != null;
        return employeeResponse;
    }

    @Description("set headers for employee")
    public void setHeaders(HttpHeaders header) {
        header.set(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_FORM_URLENCODED));
        header.set(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON));
        header.set("Authorization", "");

    }


    
}
