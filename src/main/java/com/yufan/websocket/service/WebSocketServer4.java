package com.yufan.websocket.service;

import com.yufan.websocket.bean.STOMPConnectEventListener;
import com.yufan.websocket.bean.SocketSessionRegistry;
import com.yufan.websocket.util.UserInterceptor;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
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
 * <p>
 * registry.enableSimpleBroker("/topic")表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息（比较绕，看完整个例子，大概就能明白了）
 * <p>
 * registry.setApplicationDestinationPrefixes("/app")指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketServer4 extends AbstractWebSocketMessageBrokerConfigurer {

    private Logger LOG = Logger.getLogger(WebSocketServer4.class);


    //    这个方法的作用是添加一个服务端点，来接收客户端的连接。
//    registry.addEndpoint("/socket")表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
//    withSockJS()的作用是开启SockJS支持，
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        LOG.info("------registerStompEndpoints-------");
        //注册两个STOMP的endpoint，分别用于广播和点对点
        registry.addEndpoint("/webServer").setAllowedOrigins("*").withSockJS();//广播 topic.html
        registry.addEndpoint("/queueServer").setAllowedOrigins("*").withSockJS();//点对点 queue.html
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        LOG.info("------configureMessageBroker-------");
        //表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息,topic用来广播，user用来实现p2p
        registry.enableSimpleBroker("/topic", "/user");
        //指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
        registry.setApplicationDestinationPrefixes("/app");
        //registry.setUserDestinationPrefix("/user");这句话表示给指定用户发送一对一的主题前缀是"/user"。
    }

    /**
     * 配置客户端入站通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        LOG.info("------configureClientInboundChannel-------");
        registration.interceptors(userInterceptor());
    }

    /**
     * @return
     * @Title: createUserInterceptor
     * @Description: 将客户端渠道拦截器加入spring ioc容器
     */
    @Bean
    public UserInterceptor userInterceptor() {
        LOG.info("------userInterceptor-------");
        return new UserInterceptor();

    }

}
