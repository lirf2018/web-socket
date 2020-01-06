package com.yufan.websocket.service;

import com.yufan.websocket.bean.STOMPConnectEventListener;
import com.yufan.websocket.bean.SocketSessionRegistry;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/6 17:21
 * @describe registerStompEndpoints(StompEndpointRegistry registry)
 * configureMessageBroker(MessageBrokerRegistry config)
 * 这个方法的作用是定义消息代理，通俗一点讲就是设置消息连接请求的各种规范信息。
 * registry.enableSimpleBroker("/topic")表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息（比较绕，看完整个例子，大概就能明白了）
 * registry.setApplicationDestinationPrefixes("/app")指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
 */
/*@Configuration
@EnableWebSocketMessageBroker*/
public class WebSocketServer2 extends AbstractWebSocketMessageBrokerConfigurer  {

    private Logger LOG = Logger.getLogger(WebSocketServer2.class);


    //    这个方法的作用是添加一个服务端点，来接收客户端的连接。
//    registry.addEndpoint("/socket")表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
//    withSockJS()的作用是开启SockJS支持，
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        LOG.info("----------------registerStompEndpoints---------------------------");
        registry.addEndpoint("/socket") .setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        LOG.info("----------------configureMessageBroker---------------------------");
        //表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
        registry.enableSimpleBroker("/topic");
        //指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Bean
    public SocketSessionRegistry SocketSessionRegistry(){
        LOG.info("-----SocketSessionRegistry-----");
        return new SocketSessionRegistry();
    }
    @Bean
    public STOMPConnectEventListener STOMPConnectEventListener(){
        LOG.info("-----STOMPConnectEventListener-----");
        return new STOMPConnectEventListener();
    }

}
