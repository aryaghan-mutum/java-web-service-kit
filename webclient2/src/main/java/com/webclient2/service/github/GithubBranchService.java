package com.webclient2.service.github;

import com.webclient2.response.employee.github.GithubBranchResponse;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class GithubBranchService {

    @Description("POST: create new record in database for /create service")
    public GithubBranchResponse[] getBranchesByRepo(String url)  {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(url)
                .headers(header -> header.set(HttpHeaders.ACCEPT, "application/vnd.github.v3+json"))
                .retrieve()
                .bodyToMono(GithubBranchResponse[].class)
                .onErrorResume(WebClientResponseException.class, ex -> ex.getRawStatusCode() == HttpStatus.OK.value() ? Mono.empty() : Mono.error(ex))
                .block();
    }
}
