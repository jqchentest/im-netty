package com.jqchen.netty.service.impl;

import com.jqchen.netty.bean.User;
import com.jqchen.netty.utils.NettyUtils;
import com.jqchen.netty.utils.UserUtils;
import com.jqchen.netty.service.UserService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户接口实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User login(String userName, String password) {
        return UserUtils.users.entrySet().stream()
                .filter(entry -> userName.equals(entry.getValue().getName()) && password.equals(entry.getValue().getPassword()))
                .findFirst()
                .map(Map.Entry::getValue).orElse(null);
    }

    @Override
    public void login(Long userId, ChannelHandlerContext ctx) {
        NettyUtils.userCtxs.put(userId, ctx);
        NettyUtils.writeAndFlush(ctx, UserUtils.users.get(userId).getName() + " 登录成功");
        getLeaveMessage(userId, ctx);
    }

    @Override
    public void sendMessage(Long userId, Long friendId, String message) {
        ChannelHandlerContext toCtx = NettyUtils.userCtxs.get(friendId);
        if (toCtx == null) {
            sendLeaveMessage(friendId, genMessage(userId, message));
        } else {
            NettyUtils.writeAndFlush(toCtx, genMessage(userId, message));
        }
    }

    @Override
    public void logout(Long userId, ChannelHandlerContext ctx) {
        NettyUtils.userCtxs.remove(userId);
        NettyUtils.writeAndFlush(ctx, UserUtils.users.get(userId).getName() + "退出登录");
    }

    private String genMessage(Long userId, String message) {
        return String.format("%s:%s", UserUtils.users.get(userId).getName(), message);
    }

    /**
     * 发离线消息
     */
    private void sendLeaveMessage(Long friendId, String message) {
        if (UserUtils.offlineMessage.containsKey(friendId)) {
            UserUtils.offlineMessage.get(friendId).add(message);
        } else {
            List<String> list = new ArrayList<>();
            list.add(message);
            UserUtils.offlineMessage.put(friendId, list);
        }
    }

    /**
     * 获取离线消息
     */
    private void getLeaveMessage(Long userId, ChannelHandlerContext ctx) {
        if (UserUtils.offlineMessage.containsKey(userId)) {
            for (String message : UserUtils.offlineMessage.get(userId)) {
                NettyUtils.writeAndFlush(ctx, message);
            }
        }
    }
}
