package com.jd.pop.illegal.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerMain.class);

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:jsf-provider.xml");
        LOGGER.info("服务端启动完成！");

        // 启动本地服务，然后hold住本地服务
        synchronized (ServerMain.class) {
            while (true) {
                try {
                    ServerMain.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}