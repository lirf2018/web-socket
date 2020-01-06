package com.yufan.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.yufan.websocket.bean.ReceiveMessage;
import com.yufan.websocket.log.CustomMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/6 17:29
 * @describe
 */
@Controller
public class SocketController4 {

    private Logger LOG = Logger.getLogger(SocketController4.class);

    @Autowired
    public SimpMessagingTemplate template;


    @Autowired
    private SimpUserRegistry userRegistry;

    /**
     * 广播消息
     */
    @MessageMapping("/subscribe")
    //@SendTo
    public void subscribe( ReceiveMessage rm) {
        LOG.info("服务端接收到广播消息：" + rm);
        try {
            //广播使用convertAndSend方法，第一个参数为目的地，和js中订阅的目的地要一致
            template.convertAndSend("/topic/getResponse", rm.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/subscribe2")
    public void subscribe2(HttpServletRequest request, HttpServletResponse response, ReceiveMessage rm) {
        LOG.info("服务端接收到广播消息：" + rm);
        PrintWriter writer;
        try {
            writer = response.getWriter();
            if (null == rm || StringUtils.isEmpty(rm.getMsg())) {
                String message = readStreamParameter(request.getInputStream());
                rm = JSONObject.toJavaObject(JSONObject.parseObject(message), ReceiveMessage.class);
            }
            //广播使用convertAndSend方法，第一个参数为目的地，和js中订阅的目的地要一致
            template.convertAndSend("/topic/getResponse", rm.getMsg());
            writer.print(1);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 点对点消息
     */
    @MessageMapping("/queue")
    //@SendToUser
    public void queue(ReceiveMessage rm) {
        LOG.info("服务端接收到点对点消息：" + rm);
        for (int i = 1; i <= 1; i++) {
            /*广播使用convertAndSendToUser方法，第一个参数为用户id，此时js中的订阅地址为
            "/user/" + 用户Id + "/message",其中"/user"是固定的*/
            template.convertAndSendToUser(rm.getUser(), "/message", rm.getMsg());
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

    }

    /**
     * @Description:主动推消息到客户端(可配做定时器)
     * @Author:hui.yunfei@qq.com
     * @Date: 2019/6/4
     */

    @RequestMapping("/send")
    public void send(HttpServletRequest request, HttpServletResponse response) {
        template.convertAndSend("/topic/getResponse", "我是服务器主动推送的消息");
    }

    @RequestMapping("/getOnline")
    public void getOnline(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            response.setContentType("text/html;charset=UTF-8");
            writer = response.getWriter();
            LOG.info("当前在线人数:" + userRegistry.getUserCount());
            int i = 1;
            for (SimpUser user : userRegistry.getUsers()) {
                LOG.info("用户" + i++ + "---" + user);
            }
            writer.print("当前在线人数:" + userRegistry.getUserCount());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从流中读取数据
     *
     * @param in
     * @return
     */
    private String readStreamParameter(ServletInputStream in) {
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
}
