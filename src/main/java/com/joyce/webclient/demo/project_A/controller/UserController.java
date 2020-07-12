package com.joyce.webclient.demo.project_A.controller;

import com.joyce.webclient.demo.JoyceWebclientApplication;
import com.joyce.webclient.demo.model.MoneyModel;
import com.joyce.webclient.demo.model.UserModel;
import com.joyce.webclient.demo.project_A.constant.ProjectConstant;
import com.joyce.webclient.demo.project_A.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * 演示webclient基本调用
     * @param userId
     * @return
     */
    @RequestMapping("/user/{userId}")
    public UserModel getMoney(@PathVariable Integer userId){
        UserModel model = new UserModel();
        model.setUserId(userId);
        model.setUsername("myname");

        logger.info("开始webclient请求");

        Mono<MoneyModel> moneyModelMono = userService.getMoneyModelByWebclient(userId);
//        moneyModel.cache(Duration.ofMinutes(10));

        logger.info("返回webclient请求");

        model.setMoneyModel(moneyModelMono.block());
        return model;
    }

}
