package io.txuan.im.server.application.consumer;

import io.txuan.im.common.domain.constants.IMConstants;
import io.txuan.im.common.domain.model.IMReceiveInfo;
import com.alibaba.fastjson.JSONObject;

/**
 * 中间件消息数据的基础消费者
 */
public class BaseMessageConsumer {
    /**
     * 解析数据
     */
    protected IMReceiveInfo getReceiveMessage(String msg){
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String eventStr = jsonObject.getString(IMConstants.MSG_KEY);
        return JSONObject.parseObject(eventStr, IMReceiveInfo.class);
    }
}
