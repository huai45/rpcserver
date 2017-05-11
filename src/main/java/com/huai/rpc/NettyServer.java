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

        /**
         * 客户端的Bootstrap一般用一个EventLoopGroup，而服务器端的ServerBootstrap会用到两个（这两个也可以是同一个实例）。
         * 为何服务器端要用到两个EventLoopGroup呢？
         * 这么设计有明显的好处，如果一个ServerBootstrap有两个EventLoopGroup，
         * 那么就可以把第一个EventLoopGroup用来专门负责绑定到端口监听连接事件，
         * 而把第二个EventLoopGroup用来处理每个接收到的连接
         *  如果仅由一个EventLoopGroup处理所有请求和连接的话，在并发量很大的情况下，
         *  这个EventLoopGroup有可能会忙于处理已经接收到的连接而不能及时处理新的连接请求，
         *  用两个的话，会有专门的线程来处理连接请求，不会导致请求超时的情况，大大提高了并发处理能力。
         *
         *  我们知道一个Channel需要由一个EventLoop来绑定，而且两者一旦绑定就不会再改变。
         *  一般情况下一个EventLoopGroup中的EventLoop数量会少于Channel数量，那么就很有可能出现一个多个Channel公用一个EventLoop的情况，
         *  这就意味着如果一个Channel中的EventLoop很忙的话，会影响到这个Eventloop对其它Channel的处理，
         *  这也就是为什么我们不能阻塞EventLoop的原因。
         *
         *  当然，我们的Server也可以只用一个EventLoopGroup,由一个实例来处理连接请求和IO事件
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup();    // 通过nio方式来接收连接和处理连接
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
