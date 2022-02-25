package com.sakura.cloud.websocket.controller;

import com.sakura.common.websocket.message.ContentMessage;
import com.sakura.common.websocket.message.InMessage;
import com.sakura.common.websocket.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangfan
 * @date 2022/2/25
 * @describle
 */
@RestController
@Api(value = "WebSocket控制器", tags = {"WebSocket控制器"})
public class SubController {

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 客户端发送到服务端的接口，客户端需要添加/app前缀，
     * 如需要修改前缀，在sakura-websocket脚手架中的com.sakura.common.websocket.config.WebSocketConfig类中修改
     */
    @MessageMapping("/queue/check")
    public void check() {
        InMessage<String> im = new InMessage<>();
        im.setContent("topicCheck");
        im.setTo("27");
        im.setMsgType(200);
        webSocketService.queue(im);
    }

    @GetMapping("/queue")
    @ApiOperation("queue")
    public void queue() {
        InMessage<String> im = new InMessage<>();
        im.setContent("queue");
        im.setTo("27");
        im.setMsgType(200);
        webSocketService.queue(im);
    }

    @GetMapping("/subscribe")
    @ApiOperation("subscribe")
    public void subscribe() {
        InMessage<String> im = new InMessage<>();
        im.setContent("subscribe");
        im.setTo("27");
        im.setMsgType(200);
        webSocketService.subscribe(im);
    }

    @GetMapping("/sendMessage")
    @ApiOperation("sendMessage")
    public void sendMessage(ContentMessage message) {
        webSocketService.sendMessage(message);
    }
}
