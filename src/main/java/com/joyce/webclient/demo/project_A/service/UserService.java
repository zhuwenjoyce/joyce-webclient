package com.joyce.webclient.demo.project_A.service;

import com.joyce.webclient.demo.model.MoneyModel;
import com.joyce.webclient.demo.project_A.constant.ProjectConstant;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * demo refer of :
 * https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-webclient
 */
@Service
public class UserService {

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(ProjectConstant.PROJECT_B).build();
    }

    public Mono<MoneyModel> getMoneyModelByWebclient(Integer userId) {
        return this.webClient.get().uri("/query/money", userId)
                .retrieve().bodyToMono(MoneyModel.class);
    }

}
