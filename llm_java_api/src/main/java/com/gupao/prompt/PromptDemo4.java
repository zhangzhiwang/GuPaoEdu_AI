package com.gupao.prompt;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 提示词示例 4：使用构造者模式
 *
 * @Date 2026/10/25 5:10 PM
 */
public class PromptDemo4 {
    public static void main(String[] args) throws Exception {
        AIClient client = new GPTAIClient();

//        String prompt = new PromptBuilder()
//                .role("资深的网络主播")
//                .context("你参加了中国农业产品展览会，并在其中进行带货直播")
//                .task("你要在直播中要卖一个大豆制品的饮料，生成一个吸引消费的直播文案")
//                .format("markdown")
//                .build();

        String prompt = new PromptBuilder()
                .role("资深电商运营专家")
                .context("用户25岁 女\n" +
                        "                        设计师\n" +
                        "                        喜欢旅游和数码产品")
                .task("推荐MacBook Pro")
                .constraint("1. 结合用户职业特点\n" +
                        "                     2. 结合用户兴趣爱好\n" +
                        "                     3. 突出商品卖点\n" +
                        "                     4. 生成100字以内推荐语\n" +
                        "                     5. 输出自然流畅")
                .format("小红书风格")
                .build();

        System.out.println(prompt);
        ChatResponse response = client.chatNoneStream(new ChatRequest(prompt, AIConstants.OPENAI_MODEL_3_5_TURBO, false));
        System.out.println(response.getContent());
    }
}
