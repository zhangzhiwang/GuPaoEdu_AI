package com.gupao.encapsulation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * 在GPTSimpleOpt1 的基础上继续优化
 *
 * @Date 2026/10/7 9:37 PM
 */
public class GPTSimpleOpt2 {
    private static final String URL = "https://api.openai-hk.com/v1/chat/completions";
    private static final String API_KEY = System.getenv("API_KEY");
    // 优化：将OkHttp客户端对象提取出来做成单例，并加入一些特性，比如超时、重试等
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)// 连接超时
            .readTimeout(60, TimeUnit.SECONDS)// 读超时
            .writeTimeout(60, TimeUnit.SECONDS)// 写超时
            .callTimeout(90, TimeUnit.SECONDS)// 请求超时
            .retryOnConnectionFailure(true)// 失败重试
            .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))// 连接池设置
            .build();

    public static void main(String[] args) throws Exception {
        // 构建请求体RequestBody对象
        // 优化：通过Jackson来构造json
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
        userNode.put("content", "你是谁？");
        array.add(userNode);
        root.putIfAbsent("messages", array);

        String raw = mapper.writeValueAsString(root);
        System.out.println("raw = " + raw);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), raw);// 媒体类型是 json

        // 构建请求对象
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        // 发送请求，获取响应对象
        Response response = CLIENT.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String resJson = responseBody.string();
        System.out.println("返回的原始 json：" + resJson);
        // 优化：将ResponseBody转成字符串后再进一步转成ObjectNode，这样就可以根据返回的 json 结构任意取里面的属性值了
        JsonNode resNode = mapper.readTree(resJson);
        String resText = resNode.get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
        System.out.println(resText);
    }
}
