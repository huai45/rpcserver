package com.huai.rpc;

import java.util.Map;

/**
 * Created by zhonghuai.zhang on 2017/5/10.
 */
public class MyServiceImpl implements MyService {

    @Override
    public Map hello(Map param) {
        System.out.println("  in MyServiceImpl  ,  param"+param);
        param.put("code","hello world!");
        return param;
    }

    @Override
    public Map hello2(Map param, String text, Integer num) {
        System.out.println("  in MyServiceImpl  ,  param="+param);
        System.out.println("  in MyServiceImpl  ,  text="+text);
        System.out.println("  in MyServiceImpl  ,  num="+num);
        param.put("code"," hello rpc  success! ");
        return param;
    }

    @Override
    public String hello3(Integer num) {
        System.out.println("  in hello3  ,  num="+num);
        return " I get "+num;
    }
}
