package com.eli.emailserver.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * channelInitializer
 * @author Eli
 * @date 2019/3/26
 */
@Component
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private HttpServerCodec httpServerCodec;

    @Autowired
    private ChunkedWriteHandler chunkedWriteHandler;

    @Autowired
    private HttpObjectAggregator httpObjectAggregator;

    @Autowired
    private WebSocketServerProtocolHandler webSocketServerProtocolHandler;

    @Autowired
    @Qualifier("socketFrame")
    private TextWebSocketFrameHandler textWebSocketFrameHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义channelhandler
        pipeline.addLast(textWebSocketFrameHandler);
    }
}
