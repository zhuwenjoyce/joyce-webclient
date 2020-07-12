package com.joyce.webclient.demo.project_A.controller;

import com.joyce.webclient.demo.model.MoneyModel;
import com.joyce.webclient.demo.model.UserModel;
import com.joyce.webclient.demo.project_A.constant.ProjectConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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
}
