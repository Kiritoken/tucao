package com.eli.emailserver.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * tcp 服务器 交由Spring Ioc 托管
 * scope singleton
 * @author Eli
 * @date 2019/3/26
 */
@Component
public class TcpServer {
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

   private final ServerBootstrap serverBootstrap;

   private final InetSocketAddress tcpPort;

   private ChannelFuture serverChannelFuture;

    @Autowired
    public TcpServer(@Qualifier("serverBootstrap") ServerBootstrap serverBootstrap, @Qualifier("tcpSocketAddress") InetSocketAddress tcpPort) {
        this.serverBootstrap = serverBootstrap;
        this.tcpPort = tcpPort;
    }

    @PostConstruct
   public void start() throws Exception {
      serverChannelFuture = serverBootstrap.bind(tcpPort).sync();
      logger.info("socket服务开启 监听端口号: " + tcpPort.getPort());
   }

   @PreDestroy
    public void stop() throws Exception{
       serverChannelFuture.channel().closeFuture().sync();
       logger.info("socket服务关闭");
   }
}
