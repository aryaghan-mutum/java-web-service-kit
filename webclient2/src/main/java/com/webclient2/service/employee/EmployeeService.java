package com.webclient2.service.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webclient2.request.employee2.EmployeeRequest;
import com.webclient2.response.employee.EmployeeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.webclient2.request.employee.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@Slf4j
public class EmployeeService {

    @Description("GET: get all employee data")
    public Employee getEmployeeUserResponse(String url) {
        WebClient webClient = WebClient.create();
        Employee employeeResponse = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Employee>() {
                })
                .timeout(Duration.ofSeconds(10)).block();
        return employeeResponse;
    }

    @Description("POST: create new record in database")
    public String postEmployee(String url, MultiValueMap<String, String> employeeBodyMap) {
        WebClient webClient = WebClient.create();
        String employeeResponse = webClient.post()
                .uri(url)
                .headers(header -> {
                    header.set(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_FORM_URLENCODED));
                    header.set(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON));
                    header.set("Authorization", "");
                })
                .body(BodyInserters.fromFormData(employeeBodyMap))
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(WebClientResponseException.class, ex -> ex.getRawStatusCode() == HttpStatus.OK.value() ? Mono.empty() : Mono.error(ex))
                .timeout(Duration.ofSeconds(10))
                .block();

        assert employeeResponse != null;
        return employeeResponse;
    }

    @Description("POST: create new record in database for /create service")
    public EmployeeResponse postEmployee2(String url, EmployeeRequest requestBody)  {
        WebClient webClient = WebClient.create();
        EmployeeResponse employeeResponse =  webClient.post()
                .uri(url)
                .headers(header -> {
                    header.set(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON));
                    header.set(HttpHeaders.ACCEPT, String.valueOf(MediaType.APPLICATION_JSON));
                })
                .body(Mono.just(requestBody), EmployeeResponse.class)
                .retrieve()
                .bodyToMono(EmployeeResponse.class)
                .onErrorResume(WebClientResponseException.class, ex -> ex.getRawStatusCode() == HttpStatus.OK.value() ? Mono.empty() : Mono.error(ex))
                .timeout(Duration.ofSeconds(10))
                .block();

        assert employeeResponse != null;
        return employeeResponse;
    }

    @Description("set request body for create employee service")
    public EmployeeRequest setRequestBody() {
        ObjectMapper mapper = new ObjectMapper();
        EmployeeRequest employeeRequest = new EmployeeRequest();
        try {
            employeeRequest = new ObjectMapper().readValue(new File("src/main/resources/json/employee/employee_request.json"), EmployeeRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employeeRequest;
    }

}
