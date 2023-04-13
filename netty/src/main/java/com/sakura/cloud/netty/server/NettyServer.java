package com.sakura.cloud.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class NettyServer {

    @Value("${netty.host:127.0.0.1}")
    private String host;

    @Value("${netty.port:7397}")
    private int port;

    public void serverRun() {
        //循环组接收连接，不进行处理,转交给下面的线程组
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        //循环组处理连接，获取参数，进行工作处理
        EventLoopGroup childGroup = new NioEventLoopGroup();
        InetSocketAddress address = new InetSocketAddress(host, port);
        try {
            //服务端进行启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //使用NIO模式，初始化器等等
            serverBootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyChannelInitializer());
            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(address).sync();
            log.info("tcp服务器已经启动…………");
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
