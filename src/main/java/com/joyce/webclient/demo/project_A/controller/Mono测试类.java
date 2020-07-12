package com.joyce.webclient.demo.project_A.controller;

import com.joyce.webclient.demo.util.MyPrintUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Mono测试类 {
    private static Logger logger = LoggerFactory.getLogger(Mono测试类.class);

    /**
     * defer创建对象是懒汉模式，只有subscribe订阅方法调用时才执行数据源创建：
     */
    @Test
    public void test_mono数据源组装_create方式() throws InterruptedException {
        Mono<String> mono1 = Mono.create(monoSink -> {
            monoSink.success( "create-" + MyPrintUtil.getCurrentTimeStr());
        });
        mono1.subscribe(MyPrintUtil::printValue); // create-2020-07-12 16:41:20.856
        Thread.sleep(5000);
        mono1.subscribe(MyPrintUtil::printValue); // create-2020-07-12 16:41:25.857
        Thread.sleep(5000);
        mono1.subscribe(MyPrintUtil::printValue); // create-2020-07-12 16:41:30.857
    }

    /**
     * defer创建对象是懒汉模式，只有subscribe订阅方法调用时才执行数据源创建
     */
    @Test
    public void test_mono数据源组装_defer方式() throws InterruptedException {
        Mono<String> m2 = Mono.defer(()->Mono.just( "defer-" + MyPrintUtil.getCurrentTimeStr()));
        m2.subscribe(MyPrintUtil::printValue); // defer-2020-07-12 16:41:30.859
        Thread.sleep(5000);
        m2.subscribe(MyPrintUtil::printValue); // defer-2020-07-12 16:41:35.860
    }

    /**
     * deferWithContext适合当得到一个Mono<Map>对象时使用
     */
    @Test
    public void test_mono数据源组装_deferWithContext() throws InterruptedException {
        Map<String, String> map = new HashMap<>();

        Mono<String> mono1 = Mono.deferWithContext( supplier -> {
            supplier = Context.of(new HashMap<String, String >());
            supplier.put("key1", "value1");
            logger.info("supplier.isEmpty()===" + supplier.isEmpty() + ", size=" + supplier.get("key1"));
            return Mono.create(monoSink -> {
                monoSink.success( "create-" + MyPrintUtil.getCurrentTimeStr());
            });
        });
        mono1.subscribe(MyPrintUtil::printValue);
    }

    /**
     * just创建对象是饿汉模式，当下就创建好，无论subscribe订阅几次都是这个值
     */
    @Test
    public void test_mono数据源组装_just方式() throws InterruptedException {
        Mono<String> m1 = Mono.just( "just-" + MyPrintUtil.getCurrentTimeStr());
        m1.subscribe(MyPrintUtil::printValue); // just-2020-07-12 16:41:30.857
        Thread.sleep(5000);
        m1.subscribe(MyPrintUtil::printValue); // just-2020-07-12 16:41:30.857

    }

    @Test
    public void test_mono数据源组装_delay() throws InterruptedException {

        Long start = System.currentTimeMillis();
        Disposable disposable = Mono.delay(Duration.ofSeconds(3)) // 会延时3秒再执行方法内部代码
                .subscribe(n -> {
                    logger.info("生产数据源："+ n);
                    logger.info("当前线程ID："+ Thread.currentThread().getId() + ",生产到消费耗时："+ (System.currentTimeMillis() - start));
                });
        logger.info("主线程"+ Thread.currentThread().getId() + "耗时："+ (System.currentTimeMillis() - start));
        while(!disposable.isDisposed()) {
        }
    }

}
