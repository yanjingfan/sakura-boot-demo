package com.sakura.cloud.websocket.controller;

import com.sakura.common.websocket.config.WebSocketConfig;
import com.sakura.common.websocket.message.ContentMessage;
import com.sakura.common.websocket.message.InMessage;
import com.sakura.common.websocket.service.WebSocketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther yangfan
 * @date 2022/2/25
 * @describle
 */
@RestController
@Api(value = "WebSocket发送消息", tags = {"WebSocket控制器"})
public class SubController {

    @Autowired
    private WebSocketService webSocketService;

    /**
     * 客户端发送到服务端的接口，客户端需要添加/app前缀，
     * 如需要修改前缀，在sakura-websocket脚手架中的{@link WebSocketConfig}
     */
    @MessageMapping("/queue/check")
    public void check() {
        InMessage<String> im = new InMessage<>();
        im.setContent("topicCheck");
        im.setTo("27");
        im.setMsgType(200);
        webSocketService.queue(im);
    }

    @PostMapping("/queue")
    @ApiOperation("单播")
    public void queue() {
        InMessage<String> im = new InMessage<>();
        im.setContent("queue");
        im.setTo("27");
        im.setMsgType(200);
        webSocketService.queue(im);
    }

    @PostMapping("/subscribe")
    @ApiOperation("广播")
    public void subscribe() {
        InMessage<String> im = new InMessage<>();
        im.setContent("subscribe");
        im.setTo("27");
        im.setMsgType(200);
        webSocketService.subscribe(im);
    }

    @PostMapping("/sendMessage")
    @ApiOperation("单播或广播，根据sendUrl判断")
    public void sendMessage(@RequestBody ContentMessage message) {
        webSocketService.sendMessage(message);
    }
}
