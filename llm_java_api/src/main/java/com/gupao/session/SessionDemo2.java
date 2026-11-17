package com.gupao.session;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SessionDemo1 的优化——每次请求都拼接上之前的会话
 *
 * @Date 2026/11/14 6:10 PM
 */
public class SessionDemo2 {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDemo2.class);

    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();
        String prompt1 = "我是一名java程序员，请给我推荐一款最适合搞 java 开发的笔记本电脑。注意：只推荐一款你认为最合适的即可。";
        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt1, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        LOGGER.debug("第一次回答：{}", response.getContent());
        System.out.println("-----------------------");

        // 每次请求都拼接上之前的会话
        String prompt2 = "user:" + prompt1 + "，assistant:" + response.getContent() + "，user:价格是多少？";
        response = client.chatNoneStream(new ChatRequest(prompt2, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        LOGGER.debug("第二次回答：{}", response.getContent());
    }
}
