package com.yufan.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/6 14:00
 * @describe 开启WebSocket支持
 */
/*@Configuration*/
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
