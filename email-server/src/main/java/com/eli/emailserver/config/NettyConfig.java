package com.eli.emailserver.config;

import com.eli.emailserver.netty.TextWebSocketFrameHandler;
import com.eli.emailserver.netty.WebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * netty  服务器配置启动类
 * @author Eli
 * @date 2019/3/26
 */
@Configuration
public class NettyConfig {

    private static final Logger logger = LoggerFactory.getLogger(NettyConfig.class);

    @Value("${boss.thread.count}")
    private int bossCount;

    @Value("${worker.thread.count}")
    private int workerCount;

    @Value("${tcp.port}")
    private int port;

    @Value("${so.keepalive}")
    private boolean keepAlive;

    @Value("${so.backlog}")
    private int backLog;

    @Autowired
    private WebSocketChannelInitializer webSocketChannelInitializer;

    @SuppressWarnings("unchecked")
    @Bean(name = "serverBootstrap")
    public ServerBootstrap bootstrap(){
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //childHandler
        serverBootstrap.group(bossGroup(),workerGroup()).channel(NioServerSocketChannel.class)
                        .childHandler(webSocketChannelInitializer);
        Map<ChannelOption<?>, Object> tcpChannelOptions = tcpChannelOptions();
        Set<ChannelOption<?>> keySet = tcpChannelOptions.keySet();
        //相关参数
        for (ChannelOption option : keySet) {
            serverBootstrap.option(option, tcpChannelOptions.get(option));
        }
        logger.info("netty服务注入成功");
        return serverBootstrap;
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup(){
        return new NioEventLoopGroup(bossCount);
    }

    @Bean(destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup workerGroup(){
        return new NioEventLoopGroup(workerCount);
    }


    @Bean(name = "tcpSocketAddress")
    public InetSocketAddress tcpPort() {
        return new InetSocketAddress(port);
    }

    @Bean(name = "tcpChannelOptions")
    public Map<ChannelOption<?>, Object> tcpChannelOptions() {
        Map<ChannelOption<?>, Object> options = new HashMap<ChannelOption<?>, Object>();
        options.put(ChannelOption.SO_KEEPALIVE, keepAlive);
        options.put(ChannelOption.SO_BACKLOG, backLog);
        return options;
    }

    @Bean
    public HttpServerCodec httpServerCodec(){
        return new HttpServerCodec();
    }

    @Bean
    public ChunkedWriteHandler chunkedWriteHandler(){
        return new ChunkedWriteHandler();
    }

    @Bean
    public HttpObjectAggregator httpObjectAggregator(){
        return new HttpObjectAggregator(8192);
    }

    @Bean
    public WebSocketServerProtocolHandler webSocketServerProtocolHandler(){
        return new WebSocketServerProtocolHandler("/ws");
    }

    @Bean(name = "socketFrame")
    public TextWebSocketFrameHandler textWebSocketFrameHandler(){
        return new TextWebSocketFrameHandler();
    }
}
