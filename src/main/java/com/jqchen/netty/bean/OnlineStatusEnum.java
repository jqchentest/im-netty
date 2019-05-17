package com.jqchen.netty.bean;

/**
 * 在线状态枚举
 * 2019-05-17 @author chenjunqiang
 */
public enum OnlineStatusEnum {
    ONLINE(1),
    OFFLINE(2);
    private int code;

    OnlineStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
