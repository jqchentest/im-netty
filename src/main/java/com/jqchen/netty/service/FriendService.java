package com.jqchen.netty.service;

import com.jqchen.netty.bean.Friend;

import java.util.Set;

/**
 * 好友接口
 */
public interface FriendService {

    /**
     * 获取朋友列表
     *
     * @param userId 用户id
     */
    Set<Friend> getFriends(Long userId);
}
