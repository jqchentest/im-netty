package com.jqchen.netty.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.jqchen.netty.service.socket.UserSocketService;
import com.jqchen.netty.utils.NettyUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class UserChannelHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private UserSocketService userSocketService;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        TextWebSocketFrame frame = (TextWebSocketFrame) msg;
        JSONObject json = JSONObject.parseObject(frame.text());
        switch (json.getString("type")) {
            case "login":
                userSocketService.login(json.getLong("userId"), ctx);
                break;
            case "send":
                userSocketService.sendMessage(json.getLong("userId"), json.getLong("friendId"), json.getString("content"));
                break;
            case "logout":
                userSocketService.logout(json.getLong("userId"), ctx);
                break;
            default:
                NettyUtils.writeAndFlush(ctx, "系统消息：不知道你要干嘛");
                break;
        }
    }
}
