package com.gupao.encapsulation.opt5.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gupao.encapsulation.opt5.enums.ContentTypeEnum;
import com.gupao.encapsulation.opt5.listener.StreamListener;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import static com.gupao.encapsulation.opt5.constants.AIConstants.*;
/**
 * DeepSeek 客户端
 *
 * @Date 2026/10/17 7:55 PM
 */
public class DeepSeekAIClient  extends BaseAIClient {
    private static final String URL = DEEPSEEK_URL;
    private static final String API_KEY = System.getenv("DEEP_SEEK_API_KEY");
    @Override
    protected void  getTextNoneStream(StreamListener listener, JsonNode root) throws InterruptedException {
        new GPTAIClient().getTextNoneStream(listener, root);// 在真实的 spb 项目中使用的是注入的方式引入GPTAIClient对象
    }

    @Override
    protected String getTextStream(JsonNode resNode) {
        String resText = resNode.get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
        return resText;
    }

    @Override
    protected Response call(ChatRequest request) throws Exception {
        if(request == null) {
            throw new RuntimeException("Param is empty!");
        }

        ObjectNode root = MAPPER.createObjectNode();
        root.put("model", request.getModel());
        root.put("reasoning_effort", "high");
        root.put("stream", request.isStream());

        ObjectNode thinkingNode = MAPPER.createObjectNode();
        thinkingNode.put("type", "enabled");
        root.put("thinking", thinkingNode);

        ArrayNode messagesArray = MAPPER.createArrayNode();
        ObjectNode systemRoleNode = MAPPER.createObjectNode();
        systemRoleNode.put("role", "system");
        systemRoleNode.put("content", "你是一个 AI 大模型");
        messagesArray.add(systemRoleNode);

        ObjectNode userRoleNode = MAPPER.createObjectNode();
        userRoleNode.put("role", "user");
        userRoleNode.put("content", request.getPrompt());
        messagesArray.add(userRoleNode);
        root.put("messages", messagesArray);

        String raw = MAPPER.writeValueAsString(root);
        LOGGER.debug("request body:{}", raw);
        RequestBody requestBody = RequestBody.create(MediaType.parse(ContentTypeEnum.APPLICATION_JSON.getValue()), raw);

        // 构建请求对象
        Request req = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader(AUTHORIZATION, AUTHORIZATION_PREFIX + API_KEY)
                .addHeader(CONTENT_TYPE, ContentTypeEnum.APPLICATION_JSON.getValue())
                .build();

        Response response = CLIENT.newCall(req).execute();
        CLIENT.connectionPool().evictAll();
        CLIENT.dispatcher().executorService().shutdownNow();
        return response;
    }
}
