package com.webclient2.model.service;

import com.webclient2.model.employee.Employee;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

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
    public Employee postEmployee(String url, MultiValueMap<String, String> newEmployeeMap) {
        WebClient webClient = WebClient.create();
        Employee employeeResponse = webClient.post()
                .uri(url)
                .header("Authorization", "Bearer MY_SECRET_TOKEN")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(newEmployeeMap))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Employee>() {})
                .block();
        return employeeResponse;
    }
    
}
