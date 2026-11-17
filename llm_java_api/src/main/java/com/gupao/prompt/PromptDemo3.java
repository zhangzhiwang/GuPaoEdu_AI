package com.gupao.prompt;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 提示词示例 3：使用工厂模式
 *
 * @Date 2026/10/25 5:10 PM
 */
public class PromptDemo3 {
    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();

        String prompt = PromptFactory.create("E-COMMERCE");
        System.out.println("模版替换前：" + prompt);
        Map<String, Object> map = new HashMap<>();
//        map.put("role", "资深的 java技术专家");
//        map.put("job", "java 资深开发工程师");

//        map.put("requirement", "查询 tb_user表中的所有字段并且按照年龄倒叙排序");

        map.put("age", 18);
        map.put("gender", "女");
        map.put("job", "java 开发工程师");
        map.put("interest", "编程");
        map.put("product", "MacBook Pro");
        prompt = PromptUtil.resolveTemplate(prompt, map);

        System.out.println("模版替换后：" + prompt);

        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        System.out.println(response.getContent());
    }
}
