package com.gupao.encapsulation.opt5.constants;

/**
 * 常量类
 *
 * @Date 2026/10/24 4:59 PM
 */
public interface AIConstants {
    String AUTHORIZATION = "Authorization";
    String AUTHORIZATION_PREFIX = "Bearer ";
    String CONTENT_TYPE = "Content-Type";
    String SSE_DATA_PREFIX = "data: ";
    String SSE_DONE = "[DONE]";
    String OPENAI_URL = "https://api.openai-hk.com/v1/chat/completions";
    String DEEPSEEK_URL = "https://api.deepseek.com/chat/completions";
    String OPENAI_MODEL_3_5_TURBO = "gpt-3.5-turbo";
    String DEEPSEEK_MODEL_V4_PRO = "deepseek-v4-pro";
    double DEFAULT_TEMPERATURE = 0.7;
}
