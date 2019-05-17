package com.jqchen.netty.service;

import com.jqchen.netty.bean.Friend;

import java.util.Set;

/**
 * 好友接口
 * 2019-05-17 @author chenjunqiang
 */
public interface FriendService {
    Set<Friend> getFriends(Long userId);
}
