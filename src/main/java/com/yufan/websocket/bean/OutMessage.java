package com.yufan.websocket.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lirf
 * @version 1.0
 * @date 2019/12/9 15:47
 * @describe
 */
@Setter
@Getter
public class OutMessage {

    private String content;

    public OutMessage(String content) {
        this.content = content;
    }
}
