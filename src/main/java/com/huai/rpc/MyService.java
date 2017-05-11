package com.huai.rpc;

import java.util.Map;

/**
 * Created by zhonghuai.zhang on 2017/5/10.
 */
public interface MyService {

    public Map hello(Map param);

    public Map hello2(Map param,String text,Integer num);

    public String hello3(Integer num);

}
