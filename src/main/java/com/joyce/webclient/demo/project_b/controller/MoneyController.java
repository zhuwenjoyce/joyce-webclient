package com.joyce.webclient.demo.project_b.controller;

import com.joyce.webclient.demo.model.MoneyModel;
import com.joyce.webclient.demo.project_A.controller.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MoneyController {
    private static Logger logger = LoggerFactory.getLogger(MoneyController.class);

    @RequestMapping("/query/money")
    public MoneyModel getMoneyBy(Integer userId){

        logger.info("接口/query/money，开始处理请求");

        MoneyModel model = new MoneyModel();
        model.setMoney(new BigDecimal(100));
        model.setUserId(userId);
        model.setRemark(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        logger.info("接口/query/money，结束处理请求！");

        return model;
    }

    @RequestMapping("/query/money/single-money")
    public MoneyModel getSingleMoney() throws InterruptedException {

        logger.info("接口/query/money/single-money，开始处理请求");

        MoneyModel model = new MoneyModel();
        model.setMoney(new BigDecimal(88));
        model.setUserId(10000);
        model.setRemark(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        Thread.sleep(3000);

        logger.info("接口/query/money/single-money，结束处理请求！");

        return model;
    }

    @RequestMapping("/query/money/list-money")
    public List<MoneyModel> getListMoney() throws InterruptedException {

        logger.info("接口/query/money/list-money，开始处理请求");

        List<MoneyModel> list = new ArrayList<>();

        MoneyModel model = new MoneyModel();
        model.setMoney(new BigDecimal(10));
        model.setUserId(100);
        model.setRemark(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        list.add(model);

        Thread.sleep(2000);

        MoneyModel model2 = new MoneyModel();
        model2.setMoney(new BigDecimal(20));
        model2.setUserId(200);
        model2.setRemark(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        list.add(model2);

        Thread.sleep(2000);
        logger.info("接口/query/money/list-money，结束处理请求！");

        return list;
    }


}





