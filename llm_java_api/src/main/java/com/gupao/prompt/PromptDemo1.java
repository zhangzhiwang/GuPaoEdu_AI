package com.gupao.prompt;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;

/**
 * 提示词示例 1：提示词的所有内容都写死
 *
 * @Date 2026/10/25 5:10 PM
 */
public class PromptDemo1 {
    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();
        String prompt1 = "你是一名资深Java架构师。\n" +
                "                \n" +
                "                请从以下角度分析：\n" +
                "                \n" +
                "                1. 技术成长路线\n" +
                "                2. AI发展趋势\n" +
                "                3. 职业规划\n" +
                "                \n" +
                "                当前职业：\n" +
                "                \n" +
                "                Java开发工程师";
        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt1, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        System.out.println(response.getContent());
    }
}
