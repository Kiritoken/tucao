package com.eli.emailserver.netty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 *  channel连接池
 *  Instance
 * @author Eli
 * @date 2019/3/27
 */
public class ChannelPool {

    public static ChannelGroup channelGroup =new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
