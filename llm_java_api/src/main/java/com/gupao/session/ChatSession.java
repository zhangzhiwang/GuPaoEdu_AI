package com.gupao.session;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2026/11/15 4:34 PM
 */
@Setter
@Getter
public class ChatSession {
    /** 用于会话隔离，防止聊天“串线” */
    private String sessionId;
    private List<Message> messageList = new ArrayList<>();

    public void addUserMessage(String content) {
        messageList.add(new Message("user", content));
    }

    public void addAssistantMessage(String content) {
        messageList.add(new Message("assistant", content));
    }
}
