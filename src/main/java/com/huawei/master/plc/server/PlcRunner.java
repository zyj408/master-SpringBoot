package com.huawei.master.plc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PlcRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(PlcRunner.class);

    private int port = 8182;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        NettyServer nettyServer = new NettyServer(port);

        nettyServer.setHandlers(Arrays.asList(new PlcHandler()));
        nettyServer.start();
    }
}
