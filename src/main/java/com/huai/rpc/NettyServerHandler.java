package com.huai.rpc;

import java.net.InetAddress;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by zhonghuai.zhang on 2017/5/11.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    /**
     *此方法会在接收到服务器数据后调用
     * */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(" channelRead    msg : " + msg);
        byte[] bytes = (byte[]) msg;
        Map param = (Map)MyUtil.getObject(bytes);
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " param : " + param);
        // 返回客户端消息 - 我已经接收到了你的消息
        param.put("result_code","200");
        bytes = MyUtil.getByte(param);
        ctx.writeAndFlush(bytes);
        bytes = null;
    }


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        // 收到消息直接打印输出
//        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
//        // 返回客户端消息 - 我已经接收到了你的消息
//        ctx.writeAndFlush("Received your message !\n");
//    }

    /*
     *
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

        ctx.writeAndFlush( "Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " inactive !  goodbye !");

        super.channelInactive(ctx);
    }

    /**
     *捕捉到异常
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
