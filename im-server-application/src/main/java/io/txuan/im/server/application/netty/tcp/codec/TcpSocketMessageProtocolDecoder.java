package io.txuan.im.server.application.netty.tcp.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import io.txuan.im.common.domain.constants.IMConstants;
import io.txuan.im.common.domain.model.IMUserInfo;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * tcp消息解码类
 */
public class TcpSocketMessageProtocolDecoder extends ReplayingDecoder {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() < IMConstants.MIN_READABLE_BYTES){
            return;
        }
        //获取数据包长度
        int length = byteBuf.readInt();
        ByteBuf contentBuf = byteBuf.readBytes(length);
        String content = contentBuf.toString(StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        IMUserInfo imUserInfo = objectMapper.readValue(content, IMUserInfo.class);
        list.add(imUserInfo);
    }
}
