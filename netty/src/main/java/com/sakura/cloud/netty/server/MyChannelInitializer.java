package com.sakura.cloud.netty.server;

import com.sakura.cloud.netty.codec.ObjDecoder;
import com.sakura.cloud.netty.codec.ObjEncoder;
import com.sakura.cloud.netty.domain.FileTransferProtocol;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class MyChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) {
        log.info("==================netty报告==================");
        log.info("信息：有一客户端链接到本服务端");
        log.info("IP:{}", channel.remoteAddress().getAddress());
        log.info("Port:{}", channel.remoteAddress().getPort());
        log.info("通道id:{}", channel.id().toString());
        log.info("==================netty报告完毕==================");
        //对象传输处理
        channel.pipeline().addLast(new ObjDecoder(FileTransferProtocol.class));
        channel.pipeline().addLast(new ObjEncoder(FileTransferProtocol.class));
        // 在管道中添加我们自己的接收数据实现方法
        channel.pipeline().addLast(new MyServerHandler());
    }

}
