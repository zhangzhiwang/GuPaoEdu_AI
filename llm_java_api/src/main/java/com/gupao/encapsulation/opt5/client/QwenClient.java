package com.gupao.encapsulation.opt5.client;

import com.gupao.encapsulation.opt5.listener.StreamListener;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;

/**
 * 千问客户端
 * TODO 由于时间关系此类暂未实现，等以后有时间再完善
 *
 * @Date 2026/10/24 5:42 PM
 */
public class QwenClient implements AIClient {
    @Override
    public ChatResponse chatNoneStream(ChatRequest request) throws Exception {
        return null;
    }

    @Override
    public void chatStream(ChatRequest request, StreamListener listener) throws Exception {

    }
}
