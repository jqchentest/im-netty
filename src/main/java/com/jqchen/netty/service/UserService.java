package com.jqchen.netty.service;

import com.jqchen.netty.bean.User;

import java.util.List;

/**
 * 用户接口
 */
public interface UserService {

    /**
     * 根据主键获取
     *
     * @param id
     * @return
     */
    User get(Long id);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码
     */
    User login(String userName, String password);

    /**
     * 获取离线消息
     */
    List<String> getLeaveMessages(Long userId);

    /**
     * 发离线消息
     *
     * @param friendId
     * @param message
     */
    void sendLeaveMessage(Long friendId, String message);
}
