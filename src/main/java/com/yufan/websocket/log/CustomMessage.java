package com.yufan.websocket.log;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/6 17:41
 * @describe 自定义消息
 */
@Setter
@Getter
public class CustomMessage {

    private String sid;//消息目标对象
    private String msg;//消息内容

    @Override
    public String toString() {
        return "sid:" + sid + " msg=" + msg;
    }
}
