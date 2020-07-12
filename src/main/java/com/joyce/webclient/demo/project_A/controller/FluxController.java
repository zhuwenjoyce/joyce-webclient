package com.joyce.webclient.demo.project_A.controller;

import com.joyce.webclient.demo.model.MoneyModel;
import com.joyce.webclient.demo.model.UserModel;
import com.joyce.webclient.demo.project_A.constant.ProjectConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class FluxController {

    private static Logger logger = LoggerFactory.getLogger(FluxController.class);

    @RequestMapping("/query3/user/test-flux")
    public UserModel getMoney_webclient返回flux对象(){
        UserModel model = new UserModel();
        model.setUsername("query2");

        logger.info("query3，开始webclient请求");

        // 拿到http请求结果
        WebClient.ResponseSpec responseSpec = WebClient.create()
                .get() // 这里是get请求会返回对象： WebClient.RequestHeadersUriSpec
                // .post() // 这里是post请求会返回对象： WebClient.RequestBodyUriSpec
                .uri(ProjectConstant.PROJECT_B + "/query/money/mono-test")
                .header("Accept-Charset", "utf-8") // 可以设置header
                .retrieve();

        logger.info("query3，没等到返回webclient请求，我就已经执行了");

        // 返回 Flux对象
        Flux<MoneyModel> flux = responseSpec.bodyToFlux(MoneyModel.class);

        model.setMoneyModel(flux.blockFirst());
        return model;
    }

    /**
     * 演示timout超时，及错误处理
     */
    @RequestMapping("/flux/test-timeout")
    public UserModel getMoney_webclient_error(){
        UserModel model = new UserModel();
        model.setUsername("myname-flux");

        logger.info("query2，开始webclient请求");

        // 拿到http请求结果
        WebClient.ResponseSpec responseSpec = WebClient.create()
                .get()
                .uri("http://localhost:82/project-b/query/money/single-money")
                .retrieve();

        responseSpec.onStatus(HttpStatus::is5xxServerError, clientResponse -> {
            logger.info("请求出错1：statusCode=" + clientResponse.statusCode());
            return Mono.error(new Exception(clientResponse.statusCode().value() + " error code-11111"));
        }).onStatus(HttpStatus::isError, clientResponse -> {
            logger.info("请求出错2：statusCode=" + clientResponse.statusCode());
            return Mono.error(new Exception(clientResponse.statusCode().value() + " error code-22222"));
        });

        // 返回 Flux对象
        Flux<MoneyModel> flux = responseSpec.bodyToFlux(MoneyModel.class);

        // 结果处理方式1，subscribe订阅方式
//        flux.subscribe()

        // 结果处理方式2（推荐），block方式，可以设置超时时间
        MoneyModel moneyModel = flux.blockFirst(Duration.ofSeconds(5));

        logger.info("query2，返回webclient请求");

//        model.setMoneyModel(moneyModel); // 最多等待1秒，如果没有等到结果就返回错误：IllegalStateException: Timeout on blocking read for 1000 MILLISECONDS

        model.setMoneyModel(moneyModel);
        return model;
    }
}
