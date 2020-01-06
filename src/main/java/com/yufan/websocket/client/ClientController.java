package com.yufan.websocket.client;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.yufan.websocket.service.WebSocketServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
 * @date 2019/12/6 11:08
 * @describe 客户端
 */
@Controller
@RequestMapping("/client/")
public class ClientController {

    private Logger LOG = Logger.getLogger(ClientController.class);


    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping("clientMsg")
    public void clientMsg2(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            String message = readStreamParameter(request.getInputStream());
            String msg = "";
            if (!StringUtils.isEmpty(message)) {
                JSONObject obj = JSONObject.parseObject(message);
                String code = obj.getString("code");
                JSONObject data = obj.getJSONObject("data");

                String cid = data.getString("cid");
                msg = data.getString("msg");

                //推送日志到/topic/pullLogger
                messagingTemplate.convertAndSend("/topic/pullMsg",msg);


                LOG.info("--------msg:" + msg);
            }
            writer.print("发送的消息：" + msg);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @RequestMapping("clientMsg")
    public void clientMsg(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer;
        try {
            writer = response.getWriter();
            String message = readStreamParameter(request.getInputStream());
            String msg = "";
            if (!StringUtils.isEmpty(message)) {
                JSONObject obj = JSONObject.parseObject(message);
                String code = obj.getString("code");
                JSONObject data = obj.getJSONObject("data");

                String cid = data.getString("cid");
                msg = data.getString("msg");

                WebSocketServer.sendInfo(msg, cid);
                LOG.info("--------msg:" + msg);
            }
            writer.print("发送的消息：" + msg);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

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
