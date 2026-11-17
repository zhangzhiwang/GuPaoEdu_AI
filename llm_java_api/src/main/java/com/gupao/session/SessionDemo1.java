package com.gupao.session;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多轮会话示例 1——模拟在程序中每次请求大模型都是孤立的，前后没有联系
 *
 * @Date 2026/11/14 6:10 PM
 */
public class SessionDemo1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDemo1.class);

    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();
        String prompt1 = "我是一名java程序员，请给我推荐一款最适合搞 java 开发的笔记本电脑。注意：只推荐一款你认为最合适的即可。";
        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt1, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        LOGGER.debug("第一次回答：{}", response.getContent());
        System.out.println("-----------------------");

        String prompt2 = "价格是多少？";
        response = client.chatNoneStream(new ChatRequest(prompt2, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        LOGGER.debug("第二次回答：{}", response.getContent());
        /*
         通过第二次的回答明显能够看到前后的对话是割裂的，原因是第二次请求并没有把第一次的对话内容（问和答）传给大模型，对话历史是保存在客户端本地的，
         大模型并不会保存历史对话。为什么在大模型的聊天窗口中聊天时大模型知道你之前的历史对话，是因为本地客户端本地保存了历史对话，每次请求大模型时
         实际上都是在后台默认将历史对话发送给大模型。如果历史对话比较多怎么办？岂不是很费 token？确实，记录历史会话叫做会话记忆，常见的记忆方式有四种：
         1、短期记忆
         2、摘要记忆
         3、实体记忆
         4、长期记忆
         具体见老师课堂笔记《4.多轮对话系统设计与记忆管理》
         所以在程序中不能单独地发送每次请求，必须携带历史对话，本示例需要优化。
         */
    }
}
