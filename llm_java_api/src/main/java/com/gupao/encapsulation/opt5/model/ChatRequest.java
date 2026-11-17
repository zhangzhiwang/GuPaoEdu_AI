package com.gupao.encapsulation.opt5.model;

import com.gupao.session.ChatSession;
import com.gupao.session.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * opt5包是对GPTSimpleOpt4的进一步升级，升级点包括：
 * 1、对请求参数和响应参数进行对象封装，分别封装为ChatRequest和ChatResponse
 * 2、创建访问大模型客户端的接口，不同大模型的客户端去实现该接口，比如GPTAIClient，DeepSeekAIClient
 *
 * 本类是对访问大模型输入参数的封装
 *
 * @Date 2026/10/11 2:39 PM
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
    /** 用户的输入（提示词） */
    private String prompt;
    /** 使用的模型 */
    private String model;
    /** 是否是流式响应 */
    private boolean isStream;
    /** 消息列表（存放历史消息） */
    /*
    优化：加入消息列表。加入后其实可以将prompt去掉，完全用messageList代替，但为了保留升级的过程，所以没有去掉prompt。
     */
    private List<Message> messageList = new ArrayList<>();

    public ChatRequest(String prompt, String model, boolean isStream) {
        this.prompt = prompt;
        this.model = model;
        this.isStream = isStream;
    }
}
