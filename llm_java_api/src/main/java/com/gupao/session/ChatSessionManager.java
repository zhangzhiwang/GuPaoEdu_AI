package com.gupao.session;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天会话管理器
 *
 * @Date 2026/11/15 6:23 PM
 */
@Setter
@Getter
public class ChatSessionManager {
    public static final Map<String, ChatSession> SESSION_MAP = new ConcurrentHashMap<>();// 防止并发
}
