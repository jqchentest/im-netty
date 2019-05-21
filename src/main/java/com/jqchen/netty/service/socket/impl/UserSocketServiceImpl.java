package com.jqchen.netty.service.socket.impl;

import com.alibaba.fastjson.JSONObject;
import com.jqchen.netty.bean.User;
import com.jqchen.netty.service.FriendService;
import com.jqchen.netty.service.UserService;
import com.jqchen.netty.service.socket.UserSocketService;
import com.jqchen.netty.utils.NettyUtils;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户接口实现
 */
@Service
public class UserSocketServiceImpl implements UserSocketService {
    private final UserService userService;
    private final FriendService friendService;

    public UserSocketServiceImpl(UserService userService, FriendService friendService) {
        this.userService = userService;
        this.friendService = friendService;
    }

    @Override
    public void login(Long userId, ChannelHandlerContext ctx) {
        NettyUtils.putUserCtx(userId, ctx);
        NettyUtils.writeAndFlush(ctx, userService.get(userId).getName() + " 登录成功");
        getLeaveMessage(userId, ctx);
        notifyFriendOnline(userId);
    }

    /**
     * 通知好友上线
     */
    private void notifyFriendOnline(Long userId) {
        User user = userService.get(userId);
        friendService.getFriends(userId).forEach(friend -> {
            ChannelHandlerContext ctx = NettyUtils.getUserCtx(friend.getId());
            if (ctx != null) {
                JSONObject json = new JSONObject();
                json.put("type", "friendOnline");
                json.put("friendId", userId);
                json.put("message", "你的好友" + user.getName() + "上线了");
                NettyUtils.writeAndFlush(ctx, json.toJSONString());
            }
        });
    }

    @Override
    public void sendMessage(Long userId, Long friendId, String message) {
        ChannelHandlerContext toCtx = NettyUtils.getUserCtx(friendId);
        if (toCtx == null) {
            userService.sendLeaveMessage(friendId, genMessage(userId, message));
        } else {
            NettyUtils.writeAndFlush(toCtx, genMessage(userId, message));
        }
    }

    @Override
    public void logout(Long userId, ChannelHandlerContext ctx) {
        NettyUtils.removeUserCtx(userId);
        NettyUtils.writeAndFlush(ctx, userService.get(userId).getName() + "退出登录");
    }

    private String genMessage(Long userId, String message) {
        return String.format("%s:%s", userService.get(userId).getName(), message);
    }

    /**
     * 获取离线消息
     */
    private void getLeaveMessage(Long userId, ChannelHandlerContext ctx) {
        Optional.ofNullable(userService.getLeaveMessages(userId)).map(messages -> {
            messages.forEach(message -> NettyUtils.writeAndFlush(ctx, message));
            return messages;
        });
    }
}
