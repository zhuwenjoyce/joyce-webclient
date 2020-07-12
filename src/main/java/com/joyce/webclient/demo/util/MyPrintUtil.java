package com.joyce.webclient.demo.util;

import com.joyce.webclient.demo.JoyceWebclientApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyPrintUtil {
    private static Logger logger = LoggerFactory.getLogger(MyPrintUtil.class);

    public static String getCurrentTimeStr(){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public static void printValue(Object value){
        logger.info("value === " + value);
    }

}
