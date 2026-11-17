package com.gupao.encapsulation.opt5;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.DeepSeekAIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.listener.StreamListener;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;

/**
 * 视频课程第二章-AI基础与Java生态适配（筑基篇）里面的第7-11 节课的代码都在 opt5 包下面
 * DeepSeek客户端测试类
 *
 * @Date 2026/10/11 4:10 PM
 */
public class DeepSeekAIClientTest {
    public static void main(String[] args) throws Exception {
        AIClient AIClient = new DeepSeekAIClient();
        boolean isStream = true;
        ChatRequest chatRequest = new ChatRequest("你是deepseek吗？介绍一下北京，500字左右", AIConstants.DEEPSEEK_MODEL_V4_PRO, isStream);
//        ChatResponse response = AIClient.chatNoneStream(chatRequest);
//        System.out.println(response.getContent());

        AIClient.chatStream(chatRequest, new StreamListener() {
            @Override
            public void message(String text) {
                System.out.print(text);
            }

            @Override
            public void done() {
                System.out.println();
                System.out.println("【内容读取结束】");
            }

            @Override
            public void error(Exception e) {
                System.out.println("发生错误：" + e.getMessage());
            }
        });
    }
}
