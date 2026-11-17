package com.gupao.encapsulation.opt5.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gupao.encapsulation.opt5.enums.ContentTypeEnum;
import com.gupao.encapsulation.opt5.listener.StreamListener;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.session.Message;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import static com.gupao.encapsulation.opt5.constants.AIConstants.*;

/**
 * OPENAI GPT 客户端
 *
 * @Date 2026/10/11 2:53 PM
 */
public class GPTAIClient extends BaseAIClient {
    private static final String URL = OPENAI_URL;
    private static final String API_KEY = System.getenv("GPT_API_KEY");

    /*
    * 访问大模型，请求数据
    *
    * @param request
    * @return com.gupao.encapsulation.opt5.model.ChatResponse
    * @date 2026/10/11 4:00 PM
    */
    public Response call(ChatRequest request) throws Exception {
        if(request == null) {
            throw new RuntimeException("Param is empty!");
        }

        ObjectNode root = MAPPER.createObjectNode();
        root.put("max_tokens", 1200);
        root.put("model", request.getModel());
        root.put("temperature", DEFAULT_TEMPERATURE);
        root.put("top_p", 1);
        root.put("presence_penalty", 1);
        root.put("stream", request.isStream());

        ArrayNode array = MAPPER.createArrayNode();
        ObjectNode systemNode = MAPPER.createObjectNode();
        systemNode.put("role", "system");
        systemNode.put("content", "你是一个 AI 大模型");
        array.add(systemNode);

        if(request.getMessageList().isEmpty()) {// 为了少改动这里做下兼容，见com.gupao.encapsulation.opt5.model.ChatRequest的TODO注释
            ObjectNode userNode = MAPPER.createObjectNode();
            userNode.put("role", "user");
            userNode.put("content", request.getPrompt());
            array.add(userNode);
        } else {
            for (Message msg: request.getMessageList()) {
                ObjectNode userNode = MAPPER.createObjectNode();
                userNode.put("role", msg.getRole());
                userNode.put("content", msg.getContent());
                array.add(userNode);
            }
        }

        root.putIfAbsent("messages", array);
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

        return CLIENT.newCall(req).execute();
    }

    public String getTextStream(JsonNode resNode) {
        String resText = resNode.get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
        return resText;
    }

    public void getTextNoneStream(StreamListener listener, JsonNode root) throws InterruptedException {
        ArrayNode choices = (ArrayNode) root.get("choices");
        if (choices == null || choices.size() == 0) {
            return;
        }

        JsonNode delta = choices.get(0).path("delta").path("content");
        if (delta.isMissingNode()) {
            return;
        }
        String text = delta.asText();
        if(StringUtils.isBlank(text) || "null".equals(text)) {
            return;
        }
        Thread.sleep(1);
        listener.message(text);
    }
}
