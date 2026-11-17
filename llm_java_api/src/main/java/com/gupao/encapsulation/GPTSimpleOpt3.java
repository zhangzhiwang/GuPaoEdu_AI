package com.gupao.encapsulation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 在GPTSimpleOpt2 的基础上继续优化
 *
 * @Date 2026/10/7 9:37 PM
 */
public class GPTSimpleOpt3 {
    private static final String URL = "https://api.openai-hk.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("API_KEY");
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)// 连接超时
            .readTimeout(60, TimeUnit.SECONDS)// 读超时
            .writeTimeout(60, TimeUnit.SECONDS)// 写超时
            .callTimeout(90, TimeUnit.SECONDS)// 请求超时
            .retryOnConnectionFailure(true)// 失败重试
            .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))// 连接池设置
            .build();

    public static void main(String[] args) throws Exception {
        callGPT("你是谁？");
    }

    /*
    * 优化：将原来 main 方法里面的代码提取出一个方法出来，将用户输入的信息作为入参传入
    *
    * @param promt 用户输入的对话内容
    * @return void
    * @date 2026/10/8 12:24 PM
    */
    private static void callGPT(String prompt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("max_tokens", 1200);
        root.put("model", "gpt-3.5-turbo");
        root.put("temperature", 0);
        root.put("top_p", 1);
        root.put("presence_penalty", 1);

        ArrayNode array = mapper.createArrayNode();
        ObjectNode systemNode = mapper.createObjectNode();
        systemNode.put("role", "system");
        systemNode.put("content", "你是一个 AI 大模型");
        array.add(systemNode);

        ObjectNode userNode = mapper.createObjectNode();
        userNode.put("role", "user");
        userNode.put("content", prompt);
        array.add(userNode);
        root.putIfAbsent("messages", array);

        String raw = mapper.writeValueAsString(root);
        System.out.println("raw = " + raw);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), raw);// 媒体类型是 json

        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        // 优化：增加返回是否成功的判断
        Response response = CLIENT.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String resJson = responseBody.string();
        if (!response.isSuccessful()) {
            String errMsg = responseBody == null ? "未知错误" : resJson;
            throw new RuntimeException("返回失败！code : " + response.code() + "，msg : " + errMsg);
        }

        System.out.println("返回的原始 json：" + resJson);
        JsonNode resNode = mapper.readTree(resJson);
        String resText = resNode.get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
        System.out.println(resText);
    }
}
