package com.eli.emailserver.controller;


import com.eli.emailserver.netty.ChannelPool;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.web.bind.annotation.*;

/**
 * websocket 管理员推送消息
 * @author Eli
 * @date 2019/3/27
 */
@RestController
public class PushController extends BaseController{

    @RequestMapping(value = "/push",method = RequestMethod.GET)
    @ResponseBody
    public void testPush(@RequestParam String msg){
        //群发推送消息
        ChannelPool.channelGroup.writeAndFlush(new TextWebSocketFrame(msg));
    }
}
