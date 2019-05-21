package com.jqchen.netty.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.HashMap;
import java.util.Map;

/**
 * netty工具
 */
public class NettyUtils {
    /**
     * 用户通道map
     */
    public static Map<Long, ChannelHandlerContext> userCtxs = new HashMap<>();

    public static void writeAndFlush(ChannelHandlerContext ctx, String message) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame(message));
    }

    public static ChannelHandlerContext getUserCtx(Long userId) {
        return userCtxs.get(userId);
    }

    public static void putUserCtx(Long userId, ChannelHandlerContext ctx) {
        userCtxs.put(userId, ctx);
    }

    public static void removeUserCtx(Long userId) {
        userCtxs.remove(userId);
    }
}
