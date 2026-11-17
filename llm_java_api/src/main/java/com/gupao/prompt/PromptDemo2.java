package com.gupao.prompt;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 提示词示例 2：提示词中有占位符
 *
 * @Date 2026/10/25 5:10 PM
 */
public class PromptDemo2 {
    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();

        String prompt = "你是一名{role}\n" +
                "                \n" +
                "                请从以下角度分析：\n" +
                "                \n" +
                "                1. 技术成长路线\n" +
                "                2. AI发展趋势\n" +
                "                3. 职业规划\n" +
                "                \n" +
                "                当前职业：\n" +
                "                \n" +
                "                {job}";

        System.out.println("模版替换前：" + prompt);
        Map<String, Object> map = new HashMap<>();
        map.put("role", "资深的 java技术专家");
        map.put("job", "java 资深开发工程师");
        prompt = PromptUtil.resolveTemplate(prompt, map);
        System.out.println("模版替换后：" + prompt);

        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        System.out.println(response.getContent());
    }
}
