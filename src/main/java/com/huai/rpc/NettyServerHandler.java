package com.huai.rpc;

import java.net.InetAddress;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by zhonghuai.zhang on 2017/5/11.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<byte[]> {

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg)
//            throws Exception {
//        System.out.println(" channelRead    msg : " + msg);
//        ByteBuf result = (ByteBuf) msg;
//        byte[] result1 = new byte[result.readableBytes()];
//        result.readBytes(result1);
//        Map param = (Map)MyUtil.getObject(result1);
//        System.out.println("param:" + param);
//        // 接收并打印客户端的信息
//        System.out.println("Client said:" + param);
//        // 释放资源，这行很关键
//        result.release();
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
        System.out.println(" channelRead0    msg : " + msg);
//        byte[] bytes = new byte[msg.readableBytes()];
//        msg.readBytes(bytes);
        Map param = (Map)MyUtil.getObject(msg);
        // 收到消息直接打印输出
        System.out.println(ctx.channel().remoteAddress() + " param : " + param);
        // 返回客户端消息 - 我已经接收到了你的消息

        param.put("result_code","200");
        byte[] bytes = MyUtil.getByte(param);
        ctx.writeAndFlush(bytes);
//        ctx.writeAndFlush("Received your message !\n");
    }


//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//        // 收到消息直接打印输出
//        System.out.println(ctx.channel().remoteAddress() + " Say : " + msg);
//
//        // 返回客户端消息 - 我已经接收到了你的消息
//        ctx.writeAndFlush("Received your message !\n");
//    }

    /*
     *
     * 覆盖 channelActive 方法 在channel被启用的时候触发 (在建立连接的时候)
     * channelActive 和 channelInActive 在后面的内容中讲述，这里先不做详细的描述
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

}
