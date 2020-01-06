package com.yufan.websocket.controller;

import com.yufan.websocket.bean.InMessage;
import com.yufan.websocket.bean.OutMessage;
import com.yufan.websocket.bean.SocketSessionRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/9 15:40
 * @describe 聊天控制器 消息核心控制器，发送单点消息，公共消息功能
 */
public class GreetingController {

    /*private Logger LOG = Logger.getLogger(GreetingController.class);

    *//**
     * session操作类
     *//*
    @Autowired
    private SocketSessionRegistry webAgentSessionRegistry;
    *//**
     * 消息发送工具
     *//*
    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/index")
    public String index() {
        return "/index";
    }

    @RequestMapping(value = "/msg/message")
    public String ToMessage() {
        return "/message";
    }

    @RequestMapping(value = "/msg/messaget2")
    public String ToMessaget2() {
        return "/messaget2";
    }

    *//**
     * 用户广播
     * 发送消息广播  用于内部发送使用
     *
     * @param request
     * @return
     *//*
    @GetMapping(value = "/msg/sendcommuser")
    public @ResponseBody
    OutMessage SendToCommUserMessage(HttpServletRequest request) {
        LOG.info("----------SendToCommUserMessage--------------");
        List<String> keys = webAgentSessionRegistry.getAllSessionIds().entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
        Date date = new Date();
        keys.forEach(x -> {
            String sessionId = webAgentSessionRegistry.getSessionIds(x).stream().findFirst().get();
            //convertAndSend
            template.convertAndSendToUser(sessionId, "/topic/greetings", new OutMessage("commmsg：allsend, " + "send  comm" + date.getTime() + "!"), createHeaders(sessionId));

        });
        return new OutMessage("sendcommuser, " + new Date() + "!");
    }


    *//**
     * 同样的发送消息   只不过是ws版本  http请求不能访问
     * 根据用户key发送消息
     *
     * @param message
     * @return
     * @throws Exception
     *//*
    @MessageMapping("/msg/hellosingle")
    public void greeting2(InMessage message) throws Exception {

        LOG.info("----------greeting2--------------");

        //这里没做校验
        if ("all".equals(message.getSid())) {
            //发送所有用户  这里用于广播
            //template.convertAndSend("/topic/greetings", new OutMessage("single send to：" + message.getSid() + ", from:" + message.getName() + "!"), createHeaders(sessionId));
            template.convertAndSend("/topic/greetings",new OutMessage("single send to：" + message.getSid() + ", from:" + message.getName() + "!"));
        } else {
            //指定用户发送
            String sessionId = webAgentSessionRegistry.getSessionIds(message.getSid()).stream().findFirst().get();
            //template.convertAndSendToUser(sessionId, "/topic/greetings", new OutMessage("single send to：" + message.getSid() + ", from:" + message.getName() + "!"), createHeaders(sessionId));
            template.convertAndSendToUser(sessionId, "/topic/greetings", new OutMessage("single send to：" + message.getSid() + ", from:" + message.getName() + "!"),createHeaders(sessionId));
        }
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }*/

}
