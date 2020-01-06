package com.yufan.websocket.log;

import com.yufan.websocket.service.WebSocketServer;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/6 15:52
 * @describe
 */
/*@Configuration
@EnableScheduling*/
public class ThreadLog {

    private Logger LOG = Logger.getLogger(ThreadLog.class);

    @Scheduled(cron = "0/2 * * * * ?")
    public void myJob(){
        LOG.info("-----当前在线人数为" + WebSocketServer.getOnlineCount());
    }



}
