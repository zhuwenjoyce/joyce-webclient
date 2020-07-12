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
    public void test_mono_delay() throws InterruptedException {

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

    @Test
    public void test_mono_empty() throws InterruptedException {
        Mono mono = Mono.empty();
    }

    @Test
    public void test_mono_error() throws InterruptedException {
        Mono mono = Mono.create(monoSink -> {
            int i = 1/0;
            monoSink.success( "create-" + MyPrintUtil.getCurrentTimeStr());
        }).doOnError(e->{
            System.out.println("出错了：：" + e.getMessage());
        });
    }

}
