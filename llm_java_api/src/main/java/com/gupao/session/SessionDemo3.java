package com.gupao.session;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * SessionDemo2 的优化——将历史会话放到集合里面
 *
 * @Date 2026/11/14 6:10 PM
 */
public class SessionDemo3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDemo3.class);

    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();
        String prompt1 = "我是一名java程序员，请给我推荐一款最适合搞 java 开发的笔记本电脑。注意：只推荐一款你认为最合适的即可。";
        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt1, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        LOGGER.debug("第一次回答：{}", response.getContent());
        System.out.println("-----------------------");

        ChatRequest chatRequest = new ChatRequest("", AIConstants.OPENAI_MODEL_3_5_TURBO, false);
        List<Message> messageList = chatRequest.getMessageList();
        messageList.add(new Message("user", prompt1));
        messageList.add(new Message("assistant", response.getContent()));
        messageList.add(new Message("user", "价格是多少？"));
        response = client.chatNoneStream(chatRequest);
        LOGGER.debug("第二次回答：{}", response.getContent());
    }
}
