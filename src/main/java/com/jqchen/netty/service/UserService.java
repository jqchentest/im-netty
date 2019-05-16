package com.jqchen.netty.service;

import io.netty.channel.ChannelHandlerContext;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 登录
     *
     * @param ctx
     * @param userId
     */
    void login(Long userId, ChannelHandlerContext ctx);

    /**
     * 发消息
     *
     * @param userId
     * @param friendId
     * @param message
     */
    void sendMessage(Long userId, Long friendId, String message);

    /**
     * 登出
     *
     * @param userId
     * @param ctx
     */
    void logout(Long userId, ChannelHandlerContext ctx);
}

