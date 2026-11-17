package com.gupao.session;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SessionDemo4 的优化——引入ChatSessionManager
 *
 * @Date 2026/11/14 6:10 PM
 */
public class SessionDemo5 {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDemo5.class);

    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();
        // 模拟通过上线文获取会话 id
        String sessionId = "001";
        String prompt = "我是一名java程序员，请给我推荐一款最适合搞 java 开发的笔记本电脑。注意：只推荐一款你认为最合适的即可。";
        ChatSession session = ChatSessionManager.SESSION_MAP.get(sessionId);
        if(session == null) {
            session = new ChatSession();
            session.addUserMessage(prompt);
            ChatSessionManager.SESSION_MAP.put(sessionId, session);
        } else {
            session.addUserMessage(prompt);
        }

        ChatRequest chatRequest = new ChatRequest("", AIConstants.OPENAI_MODEL_3_5_TURBO, false);
        chatRequest.getMessageList().addAll(session.getMessageList());
        ChatResponse response = client.chatNoneStream(chatRequest);
        LOGGER.debug("SessionDemo5第一次回答：{}", response.getContent());
        // 大模型返回后将返回的内容放到历史信息里面
        session.addAssistantMessage(response.getContent());
        System.out.println("-----------------------");


        // 模拟通过上线文获取会话 id
        sessionId = "001";
        prompt = "价格是多少？";
        session = ChatSessionManager.SESSION_MAP.get(sessionId);
        if(session == null) {
            session = new ChatSession();
            session.addUserMessage(prompt);
            ChatSessionManager.SESSION_MAP.put(sessionId, session);
        } else {
            session.addUserMessage(prompt);
        }

        chatRequest = new ChatRequest("", AIConstants.OPENAI_MODEL_3_5_TURBO, false);
        chatRequest.getMessageList().addAll(session.getMessageList());
        response = client.chatNoneStream(chatRequest);
        LOGGER.debug("SessionDemo5第二次回答：{}", response.getContent());
        // 大模型返回后将返回的内容放到历史信息里面
        session.addAssistantMessage(response.getContent());
        System.out.println("-----------------------");







//        chatRequest = new ChatRequest("", AIConstants.OPENAI_MODEL_3_5_TURBO, false);
//        ChatSession chatSession = new ChatSession();
//        chatSession.addUserMessage(prompt);
//        chatSession.addAssistantMessage(response.getContent());
//        chatSession.addUserMessage("价格是多少？");
//        chatRequest.getMessageList().addAll(chatSession.getMessageList());
//
//        response = client.chatNoneStream(chatRequest);
//        LOGGER.debug("SessionDemo4第二次回答：{}", response.getContent());
    }
}
