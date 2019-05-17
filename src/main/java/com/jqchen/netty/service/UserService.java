package com.jqchen.netty.service;

import com.jqchen.netty.bean.User;
import io.netty.channel.ChannelHandlerContext;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     */
    User login(String userName, String password);

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

