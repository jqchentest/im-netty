package com.jqchen.netty.common;

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
}