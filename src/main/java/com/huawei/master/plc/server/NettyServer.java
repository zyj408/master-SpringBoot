package com.huawei.master.plc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

public class NettyServer {

    /**
     * 线程执行线程
     */
    private Thread serverThread;

    /**
     * 回调函数
     */
    private List<ChannelHandler> handlers = new ArrayList<ChannelHandler>();

    /**
     * ip地址
     */
    private String host;

    /**
     * 端口号
     */
    private int port;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public NettyServer(int port) {
        this.port = port;
    }

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        serverThread = new Thread(()->{
            try {
                ServerBootstrap server = new ServerBootstrap();
                server.group(bossGroup, workerGroup);
                server.channel(NioServerSocketChannel.class);
                server.childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("decoder", new ByteArrayDecoder());
                        pipeline.addLast("encoder", new ByteArrayEncoder());
                        if (CollectionUtils.isNotEmpty(handlers)) {
                            pipeline.addLast(handlers.toArray(new ChannelHandler[handlers.size()]));
                        }
                    }
                });
                // 设置长连接
                server.option(ChannelOption.SO_KEEPALIVE, true);
                server.option(ChannelOption.SO_BACKLOG, 1024);
                // 服务器绑定端口监听
                ChannelFuture f = StringUtils.isEmpty(host) ? server.bind(port).sync() : server.bind(host, port).sync();

                System.out.println("netty server[" + port + "] start success...");

                // 监听服务器关闭监听
                f.channel().closeFuture().sync();

            } catch (InterruptedException e) {
                System.out.println(ExceptionUtils.getStackTrace(e));
            } finally {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();

            }
        });
        serverThread.start();
    }

    public void close() {
        if(serverThread != null)
        {
            serverThread.interrupt();
        }
        System.out.println("netty server close success...");
    }


    public List<ChannelHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<ChannelHandler> handlers) {
        this.handlers = handlers;
    }

}
