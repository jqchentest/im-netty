package com.jqchen.netty.netty;

import com.jqchen.netty.netty.handler.UserChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NettyServer {

    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ChannelFuture channelFuture;
    private final UserChannelHandler userChannelHandler;

    public NettyServer(UserChannelHandler userChannelHandler) {
        this.userChannelHandler = userChannelHandler;
    }

    void init() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            channelFuture = serverBootstrap
                    .group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024) //配置TCP参数，握手字符串长度设置
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2
                    // 小时没有交流，机制会被启动
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(592048))//配置固定长度接收缓存区分配器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/"));
                            ch.pipeline().addLast(userChannelHandler);
                        }
                    })
                    .bind(8988).sync();
            if (channelFuture.isSuccess()) {
                System.out.println("netty启动成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            e.printStackTrace();
        }
    }

    void close() {
        channelFuture.channel().close();
        Future<?> bossGroupFuture = boss.shutdownGracefully();
        Future<?> workerGroupFuture = worker.shutdownGracefully();
        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
