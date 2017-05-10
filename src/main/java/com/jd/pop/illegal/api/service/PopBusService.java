package com.jd.pop.illegal.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by zhonghuai.zhang on 2017/4/14.
 */
public class PopBusService implements PopBuzService {

    private static Logger logger = LoggerFactory
            .getLogger(PopBusService.class);

    public String sayHello(String str) {
        logger.info("server get request : {}", str);
        return "hello , "+str;
    }
}
