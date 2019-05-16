package com.jqchen.netty.netty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Netty 启动类
 */
@Component
@Slf4j
public class NettyApplication {
    private final NettyServer nettyServer;

    public NettyApplication(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @PostConstruct
    public void init() throws InterruptedException {
        nettyServer.init();
//        nettyClient.init();
    }
    @PreDestroy
    public void close() {
        log.info("正在释放Netty Websocket相关连接...");
        nettyServer.close();
    }
}
