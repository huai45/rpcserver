package com.huai.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by zhonghuai.zhang on 2017/5/10.
 */
public class MyServerInHandler  extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(MyServerInHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        logger.info("HelloServerInHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        Map param = MyUtil.getObject(result1);
        System.out.println("param:" + param);
        // 接收并打印客户端的信息
        System.out.println("Client said:" + param);
        // 释放资源，这行很关键
        result.release();

        String method = param.get("method").toString();
        Class[] parameterTypes = (Class[])param.get("paramTypes");
        Object[] params = ( Object[])param.get("params");
        Class onwClass = Class.forName("com.huai.rpc.MyServiceImpl");
        Object obj = onwClass.newInstance();
        Method m = obj.getClass().getDeclaredMethod(method, parameterTypes);
        //调用方法
        Map  return_map = (Map) m.invoke(obj, params);
        System.out.println("return_map:"+return_map);

        // 向客户端发送消息
        byte[] b = MyUtil.getByte(return_map);
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * b.length);
        encoded.writeBytes(b);
        ctx.write(encoded);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
