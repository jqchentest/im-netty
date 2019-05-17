package com.jqchen.netty.service.impl;

import com.jqchen.netty.bean.Friend;
import com.jqchen.netty.bean.OnlineStatusEnum;
import com.jqchen.netty.service.FriendService;
import com.jqchen.netty.utils.NettyUtils;
import com.jqchen.netty.utils.UserUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FriendServiceImpl implements FriendService {

    @Override
    public Set<Friend> getFriends(Long userId) {
        Set<Friend> friends = UserUtils.friends.get(userId);
        friends.forEach(friend -> {
            if (NettyUtils.userCtxs.containsKey(friend.getId())) {
                friend.setOnlineStatus(OnlineStatusEnum.ONLINE);
            }
        });
        return friends;
    }
}
