package com.webclient2.model.service;

import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import com.webclient2.model.employee.*;
import java.time.Duration;

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

    //NEEDS FIX
    @Description("POST: create new record in database")
    public String postEmployee(String url, MultiValueMap<String, String> newEmployeeBodyMap) {
        WebClient webClient = WebClient.create();
        String employeeResponse = webClient.post()
                .uri(url)
                .header("Authorization", "")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(newEmployeeBodyMap))
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .block();
        return employeeResponse;
    }
    
}
