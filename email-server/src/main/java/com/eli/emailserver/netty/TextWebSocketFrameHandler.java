package com.eli.emailserver.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * websocket handler
 * text frame
 * @author Eli
 * @date 2019/3/26
 */
@Component
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(TextWebSocketFrameHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info(ctx.channel().remoteAddress().toString()+ "建立与服务器的连接");
        //添加至ChannelPool，推送管理
        ChannelPool.channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx)  {
        logger.info(ctx.channel().remoteAddress().toString() + "断开与服务器之间的连接");
        //remove channel from ChannelPool
        ChannelPool.channelGroup.remove(ctx.channel());
    }

    /**
     * 可读
     * @param channelHandlerContext 上下文
     * @param textWebSocketFrame frame
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                TextWebSocketFrame textWebSocketFrame)  {
        logger.info("收到消息： " + textWebSocketFrame.text() );

/*        channelHandlerContext.writeAndFlush(
                new TextWebSocketFrame("服务器已经收到来自 : "
                        + channelHandlerContext.channel().remoteAddress() + "的消息 " + LocalDateTime.now()));*/
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("发生异常 ");
        super.exceptionCaught(ctx, cause);
    }
}
