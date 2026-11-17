package com.gupao.encapsulation;

import okhttp3.*;

/**
 * 通过OkHttp对 GPT 的简单调用
 *
 * @Date 2026/10/7 9:06 PM
 */
public class GPTSimple {
    public static void main(String[] args) throws Exception {
        // 创建OkHttp客户端对象
        OkHttpClient client = new OkHttpClient();

        // 构建请求体RequestBody对象
        String raw = "{\n" +
                "    \"max_tokens\": 1200,\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"temperature\": 0,\n" +
                "    \"top_p\": 1,\n" +
                "    \"presence_penalty\": 1,\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"你是一个 AI 大模型\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"描述一下绿色的草原\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), raw);// 媒体类型是 json

        // 构建请求对象
        Request request = new Request.Builder()
                .url("https://api.openai-hk.com/v1/chat/completions")
                .post(requestBody)
                .addHeader("Authorization", "Bearer hk-2wxvpj100006505229b6e11880807b143716c67448d00abb")
                .addHeader("Content-Type", "application/json")
                .build();

        // 发送请求，获取响应对象
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.string());
    }
}
