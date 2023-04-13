package com.sakura.cloud.gateway;

import com.sakura.cloud.netty.client.NettyClient;
import com.sakura.cloud.netty.domain.FileTransferProtocol;
import com.sakura.cloud.netty.util.MsgUtil;
import io.netty.channel.ChannelFuture;

import java.io.File;

public class NettyClientTest {

    public static void main(String[] args) {

        //启动客户端
        ChannelFuture channelFuture = new NettyClient().connect("127.0.0.1", 7397);

        //文件信息{文件大于1024kb方便测试断点续传}
        File file = new File("D:\\yangfan\\edge\\Downloads\\wltszs_37000.zip");
        FileTransferProtocol fileTransferProtocol = MsgUtil.buildRequestTransferFile(file.getAbsolutePath(), file.getName(), file.length());

        //发送信息；请求传输文件
        channelFuture.channel().writeAndFlush(fileTransferProtocol);

    }

}
