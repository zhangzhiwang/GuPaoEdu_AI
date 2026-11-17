package com.gupao.encapsulation.opt5;

import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.constants.AIConstants;
import com.gupao.encapsulation.opt5.listener.StreamListener;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 视频课程第二章-AI基础与Java生态适配（筑基篇）里面的第7-14 节课的代码都在 opt5 包下面
 * GPT客户端测试类
 *
 * @Date 2026/10/11 4:10 PM
 */
public class GPTAIClientTest {
    private static Logger LOGGER = LoggerFactory.getLogger(GPTAIClientTest.class);

    public static void main(String[] args) throws Exception {
        AIClient AIClient = new GPTAIClient();
        boolean isStream = true;
        ChatRequest chatRequest = new ChatRequest("介绍一下北京，500字左右", AIConstants.OPENAI_MODEL_3_5_TURBO, isStream);
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
                LOGGER.info("【内容读取结束】");
            }

            @Override
            public void error(Exception e) {
                LOGGER.info("发生错误：{}", e.getMessage());
            }
        });
    }
}
