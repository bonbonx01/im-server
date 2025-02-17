package io.txuan.im.server.application.netty.processor.factory;

import io.txuan.im.common.domain.enums.IMCmdType;
import io.txuan.im.server.application.netty.processor.MessageProcessor;
import io.txuan.im.server.application.netty.processor.impl.GroupMessageProcessor;
import io.txuan.im.server.application.netty.processor.impl.LoginProcessor;
import io.txuan.im.server.application.netty.processor.impl.PrivateMessageProcessor;
import io.txuan.im.server.infrastructure.holder.SpringContextHolder;
import io.txuan.im.server.application.netty.processor.impl.HeartbeatProcessor;

public class ProcessorFactory {
    public static MessageProcessor<?> getProcessor(IMCmdType cmd){
        switch (cmd){
            //登录
            case LOGIN:
                return SpringContextHolder.getApplicationContext().getBean(LoginProcessor.class);
            //心跳
            case HEART_BEAT:
                return SpringContextHolder.getApplicationContext().getBean(HeartbeatProcessor.class);
                //单聊消息
            case PRIVATE_MESSAGE:
                return SpringContextHolder.getApplicationContext().getBean(PrivateMessageProcessor.class);
                //群聊消息
            case GROUP_MESSAGE:
                return SpringContextHolder.getApplicationContext().getBean(GroupMessageProcessor.class);

            default:
                return null;

        }
    }
}
