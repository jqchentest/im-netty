package com.jqchen.netty.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {

    private Boolean success;
    private String msg;
    private T result;

    public ResponseVO(String msg) {
        this.success = false;
        this.msg = msg;
    }

    public ResponseVO(T result) {
        this.success = true;
        this.result = result;
    }
}
