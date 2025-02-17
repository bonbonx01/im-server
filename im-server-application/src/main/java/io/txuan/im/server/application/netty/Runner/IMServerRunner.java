package io.txuan.im.server.application.netty.Runner;

import cn.hutool.core.collection.CollectionUtil;
import io.txuan.im.server.application.netty.IMNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.List;

/**
 * 服务组
 */
@Component
public class IMServerRunner implements CommandLineRunner {
    @Autowired
    private List<IMNettyServer> imNettyServers;
    @Override
    public void run(String... args) throws Exception {
        if (!CollectionUtil.isEmpty(imNettyServers)){
            imNettyServers.forEach(IMNettyServer::start);
        }
    }
    @PreDestroy
    public void destroy(){
        if (!CollectionUtil.isEmpty(imNettyServers)){
            imNettyServers.forEach(IMNettyServer::shutdown);
        }
    }

    public boolean isReady(){
        for(IMNettyServer imNettyServer : imNettyServers){
            if(!imNettyServer.isReady()){
                return false;
            }
        }
        return true;
    }
}
