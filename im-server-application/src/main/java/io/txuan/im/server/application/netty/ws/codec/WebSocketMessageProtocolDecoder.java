package io.txuan.im.server.application.netty.ws.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.txuan.im.common.domain.model.IMSendInfo;

import java.util.List;

/**
 * websocket解码器
 */
public class WebSocketMessageProtocolDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame, List list) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        IMSendInfo imSendInfo = objectMapper.readValue(textWebSocketFrame.text(), IMSendInfo.class);
        list.add(imSendInfo);
    }
}
