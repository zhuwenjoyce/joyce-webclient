package com.joyce.webclient.demo.project_A.controller;

import com.joyce.webclient.demo.model.MoneyModel;
import com.joyce.webclient.demo.model.UserModel;
import com.joyce.webclient.demo.util.MyPrintUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Flux 和 Mono 的区别 https://www.cnblogs.com/cag2050/p/11552278.html
 * Flux 和 Mono 是 Reactor 中的两个基本概念。
 *
 * Flux 表示的是包含 0 到 N 个元素的异步序列。在该序列中可以包含三种不同类型的消息通知：正常的包含元素的消息、序列结束的消息和序列出错的消息。
 * 当消息通知产生时，订阅者中对应的方法 onNext(), onComplete()和 onError()会被调用。
 *
 * Mono 表示的是包含 0 或者 1 个元素的异步序列。该序列中同样可以包含与 Flux 相同的三种类型的消息通知。
 *
 * Flux 和 Mono 之间可以进行转换。
 * 对一个 Flux 序列进行计数操作，得到的结果是一个 Mono对象。
 * 把两个 Mono 序列合并在一起，得到的是一个 Flux 对象。
 */
@RestController
public class MonoController {
    private static Logger logger = LoggerFactory.getLogger(MonoController.class);

    /**
     * 演示webclient基本调用
     * @param userId
     * @return
     */
    @RequestMapping("/query2/user/test-mono")
    public UserModel getMoney_webclient返回Mono对象(@PathVariable Integer userId){
        UserModel model = new UserModel();
        model.setUserId(userId);
        model.setUsername("myname");

        logger.info("query2，开始webclient请求");

        // 拿到http请求结果
        WebClient.ResponseSpec responseSpec = WebClient.create()
                .get()
                .uri("/query/money/single-money", userId)
                .retrieve();

        // 返回 Mono对象
        Mono<MoneyModel> mono = responseSpec.bodyToMono(MoneyModel.class);

        mono.delayElement(Duration.ofSeconds(2));

        logger.info("query2，返回webclient请求");

        model.setMoneyModel(mono.block());
        return model;
    }

    @Test
    public void test1(){
        UserModel userModel = new UserModel();
        userModel.setUserId(0);

        Mono.create(monoSink -> {
            for (int i = 5; i < 10; i++) {
                monoSink.success(i + userModel.getUserId()); // 由于会优先执行doFirst方法，所以这里的 userModel.getUserId() = 100
            }
            monoSink.success("I'm king.");
        }).doFirst(new Runnable() {
            @Override
            public void run() {
                userModel.setUserId(100);
                System.out.println("doFirst, 这是一个线程在执行mono");
            }
        }).doFinally( signalType -> {
            if(SignalType.ON_COMPLETE == signalType){
                System.out.println("状态：执行完毕");
            } else if(SignalType.ON_ERROR == signalType){
                System.out.println("状态：返回error");
            } else {
                System.out.println("状态：其他状态：" + signalType.name());
            }
        }).subscribe(System.out::println);

    }
}
