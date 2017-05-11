package com.huai.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by zhonghuai.zhang on 2017/5/11.
 */
public class NettyServer {

    /**
     * 服务端监听的端口地址
     */
    private static final int portNumber = 8000;

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();  // 通过nio方式来接收连接和处理连接
        try {
            ServerBootstrap b = new ServerBootstrap();  // 引导辅助程序
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);  // 设置nio类型的channel
            b.childHandler(new NettyServerInitializer());  //有连接到达时会创建一个channel

            // 服务器绑定端口监听
            ChannelFuture f = b.bind(portNumber).sync(); // 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();  // 应用程序会一直等待，直到channel关闭

            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        } finally {
            //关闭EventLoopGroup，释放掉所有资源包括创建的线程
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
