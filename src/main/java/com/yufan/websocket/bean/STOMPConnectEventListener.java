package com.yufan.websocket.bean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/9 15:38
 * @describe STOMP监听类 用于用户session注入
 */
public class STOMPConnectEventListener  {//implements ApplicationListener<SessionConnectEvent>

    private Logger LOG = Logger.getLogger(STOMPConnectEventListener.class);

    /*@Autowired
    SocketSessionRegistry webAgentSessionRegistry;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        LOG.info("-------onApplicationEvent------------");
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //login get from browser
        String agentId = sha.getNativeHeader("cid").get(0);
        String sessionId = sha.getSessionId();
        webAgentSessionRegistry.registerSessionId(agentId, sessionId);
    }*/
}
