package io.txuan.im.server.application.netty.processor.impl;

import io.netty.channel.ChannelHandlerContext;
import io.txuan.im.common.domain.constants.IMConstants;
import io.txuan.im.common.domain.enums.IMCmdType;
import io.txuan.im.common.domain.enums.IMSendCode;
import io.txuan.im.common.domain.model.IMReceiveInfo;
import io.txuan.im.common.domain.model.IMSendInfo;
import io.txuan.im.common.domain.model.IMSendResult;
import io.txuan.im.common.domain.model.IMUserInfo;
import io.txuan.im.common.mq.MessageSenderService;
import io.txuan.im.server.application.netty.cache.UserChannelContextCache;
import io.txuan.im.server.application.netty.processor.MessageProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 群消息处理器
 */
public class GroupMessageProcessor implements MessageProcessor<IMReceiveInfo> {
    private final Logger logger = LoggerFactory.getLogger(GroupMessageProcessor.class);

    @Autowired
    private MessageSenderService messageSenderService;

    @Async
    @Override
    public void process(IMReceiveInfo receiveInfo) {
        IMUserInfo sender = receiveInfo.getSender();
        List<IMUserInfo> receivers = receiveInfo.getReceivers();
        logger.info("GroupMessageProcessor.process|接收到群消息,发送消息用户:{}，接收消息用户数量:{}，消息内容:{}", sender.getUserId(), receivers.size(), receiveInfo.getData());
        receivers.forEach((receiver)->{
            try{
                ChannelHandlerContext channelCtx = UserChannelContextCache.getChannelCtx(receiver.getUserId(), receiver.getTerminal());
                if(channelCtx != null){
                    // 向用户推送消息
                    IMSendInfo<?> imSendInfo = new IMSendInfo<>(IMCmdType.GROUP_MESSAGE.code(), receiveInfo.getData());
                    channelCtx.writeAndFlush(imSendInfo);
                    // 发送确认消息
                    sendResult(receiveInfo, receiver, IMSendCode.SUCCESS);
                }else{
                    sendResult(receiveInfo, receiver, IMSendCode.NOT_FIND_CHANNEL);
                    logger.error("GroupMessageProcessor.process|未找到Channel,发送者:{}, 接收者:{}, 消息内容:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData());
                }
            }catch (Exception e){
                sendResult(receiveInfo, receiver, IMSendCode.UNKNOW_ERROR);
                logger.error("GroupMessageProcessor.process|发送消息异常,发送者:{}, 接收者:{}, 消息内容:{}, 异常信息:{}", sender.getUserId(), receiver.getUserId(), receiveInfo.getData(), e.getMessage());
            }
        });
    }

    /**
     * 发送结果数据
     */
    private void sendResult(IMReceiveInfo imReceiveInfo, IMUserInfo imUserInfo, IMSendCode imSendCode){
        if (imReceiveInfo.getSendResult()){
            IMSendResult<?> imSendResult = new IMSendResult<>(imReceiveInfo.getSender(), imUserInfo, imSendCode.code(), imReceiveInfo.getData());
            imSendResult.setDestination(IMConstants.IM_RESULT_GROUP_QUEUE);
            messageSenderService.send(imSendResult);
        }
    }
}
