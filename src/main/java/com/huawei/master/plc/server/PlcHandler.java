package com.huawei.master.plc.server;

import com.huawei.master.core.utils.SpringContextHolder;
import com.huawei.master.plc.service.PlcService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;


public class PlcHandler extends SimpleChannelInboundHandler<byte[]> {

    private static PlcService plcService = SpringContextHolder.getApplicationContext().getBean("plcService", PlcService.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] s) throws Exception {

        String uuid = ctx.channel().id().asLongText();
        String resp = plcService.handle(uuid, new String(s));
        if (StringUtils.isNotEmpty(resp)) {
            ctx.writeAndFlush(resp);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[play-thread] RamoteAddress : " + ctx.channel().remoteAddress() + " active...");
        String uuid = ctx.channel().id().asLongText();

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[play-thread] RamoteAddress : " + ctx.channel().remoteAddress() + " inactive...");
        String uuid = ctx.channel().id().asLongText();
        super.channelInactive(ctx);
    }
}
