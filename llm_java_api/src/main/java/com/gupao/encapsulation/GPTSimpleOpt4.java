package com.gupao.encapsulation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 在GPTSimpleOpt3 的基础上继续优化
 *
 * @Date 2026/10/7 9:37 PM
 */
public class GPTSimpleOpt4 {
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
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        callGPT("介绍一下北京，500字左右", true);
    }

    private static void callGPT(String prompt, boolean isStream) throws Exception {

        ObjectNode root = MAPPER.createObjectNode();
        root.put("max_tokens", 1200);
        root.put("model", "gpt-3.5-turbo");
        root.put("temperature", 0);
        root.put("top_p", 1);
        root.put("presence_penalty", 1);
        root.put("stream", isStream);

        ArrayNode array = MAPPER.createArrayNode();
        ObjectNode systemNode = MAPPER.createObjectNode();
        systemNode.put("role", "system");
        systemNode.put("content", "你是一个 AI 大模型");
        array.add(systemNode);

        ObjectNode userNode = MAPPER.createObjectNode();
        userNode.put("role", "user");
        userNode.put("content", prompt);
        array.add(userNode);
        root.putIfAbsent("messages", array);

        String raw = MAPPER.writeValueAsString(root);
//        System.out.println("raw = " + raw);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), raw);

        // 构建请求对象
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = CLIENT.newCall(request).execute();
        if (!response.isSuccessful()) {
            String errMsg = response.body() == null ? "未知错误" : response.body().string();
            throw new RuntimeException("返回失败！code : " + response.code() + "，msg : " + errMsg);
        }

        if(isStream) {
            dealResStream(response);
        } else {
            dealResNoneStream(response);
        }
    }

    /*
    * 处理流式响应
    *
    * @param response
    * @return void
    * @date 2026/10/8 4:49 PM
    */
    private static void dealResStream(Response response) throws Exception {
        if(response == null) {
            throw new RuntimeException("入参不能为空！");
        }

        ResponseBody responseBody = response.body();
        okio.BufferedSource source = responseBody.source();
        while(!source.exhausted()) {// exhaust v.耗尽，用光
            String line = source.readUtf8Line();
//            System.out.println("line:" + line);
            if(StringUtils.isBlank(line) || !line.startsWith("data:")) {
                continue;
            }

            String json = line.substring(6);
            if("[DONE]".equals(json)) {
                break;
            }

            JsonNode root = MAPPER.readTree(json);
            /*
            JsonNode取某个属性的值可以使用 get()方法，也可以使用 path()方法，二者的效果是一样的。只不过 get 方法在某属性不存在时返回 null，
            而 path 方法会返回一个特殊的节点叫MissingNode，以防止NPE。
             */
            ArrayNode choices = (ArrayNode) root.get("choices");
            if(choices == null || choices.size() == 0) {
                continue;
            }

            JsonNode delta = choices.get(0).path("delta").path("content");
            if(delta.isMissingNode()) {
                continue;
            }
            String text = delta.asText();
            Thread.sleep(1);
            System.out.print(text);
        }
    }

    /*
    * 处理非流式响应
    *
    * @param response
    * @return void
    * @date 2026/10/8 4:42 PM
    */
    private static void dealResNoneStream(Response response) throws IOException {
        if(response == null) {
            throw new RuntimeException("入参不能为空！");
        }

        ResponseBody responseBody = response.body();
        String resJson = responseBody.string();
        System.out.println("返回的原始 json：" + resJson);
        JsonNode resNode = MAPPER.readTree(resJson);
        String resText = resNode.get("choices")
                .get(0)
                .get("message")
                .get("content")
                .asText();
        System.out.println(resText);
    }
}
