package com.gupao.functionCall;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gupao.encapsulation.opt5.client.AIClient;
import com.gupao.encapsulation.opt5.client.GPTAIClient;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import okhttp3.*;

/**
 * 函数调用示例 1
 *
 * @Date 2026/11/17 9:39 PM
 */
public class FunctionCallDemo1 {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static void main(String[] args) throws Exception {
        AIClient aiClient = new GPTAIClient();
        String prompt = "北京今天的天气怎么样？";// 大模型反回：抱歉，我无法提供实时天气信息。建议您查看天气预报网站或使用手机应用程序获取最新的天气情况。所以，需要函数调用，
        ChatResponse chatResponse = aiClient.chatNoneStream(new ChatRequest(prompt, "gpt-3.5-turbo", false));
        System.out.println(chatResponse.getContent());
        System.out.println("-------------------");

        /*
         按理来说应该在chatNoneStream()方法里用程序拼接函数调用部分的报文，所需的信息（比如函数名称和参数等）通过变量传进来，但是这样会写很多代码，
         毕竟这个不是正式的项目，只是个练习工程，所以下面以最原始的方式来展示在函数调用中传给大模型的报文是什么样的。
         */
        OkHttpClient client = new OkHttpClient();

        // 加入函数调用部分的报文
        String raw = "{\n" +
                "    \"max_tokens\": 1200,\n" +
                "    \"model\": \"gpt-3.5-turbo\",\n" +
                "    \"temperature\": 0,\n" +
                "    \"top_p\": 1,\n" +
                "    \"presence_penalty\": 1,\n" +
                "    \"stream\": false,\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"system\",\n" +
                "            \"content\": \"你是一个 AI 大模型\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"北京今天的天气怎么样？\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"tools\": [\n" +
                "        {\n" +
                "            \"type\": \"function\",\n" +
                "            \"function\": {\n" +
                "                \"name\": \"get_weather\",\n" +
                "                \"description\": \"查询指定城市天气\",\n" +
                "                \"parameters\": {\n" +
                "                    \"type\": \"object\",\n" +
                "                    \"properties\": {\n" +
                "                        \"city\": {\n" +
                "                            \"type\": \"string\",\n" +
                "                            \"description\": \"城市名称\"\n" +
                "                        }\n" +
                "                    },\n" +
                "                    \"required\": [\n" +
                "                        \"city\"\n" +
                "                    ]\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "    ],\n" +
                "    \"tool_choice\": \"required\"\n" +
                "}";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), raw);// 媒体类型是 json

        // 构建请求对象
        Request request = new Request.Builder()
                .url("https://api.openai-hk.com/v1/chat/completions")
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + System.getenv("GPT_API_KEY"))
                .addHeader("Content-Type", "application/json")
                .build();

        // 发送请求，获取响应对象
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String resJson = responseBody.string();
        System.out.println(resJson);

        JsonNode root = MAPPER.readTree(resJson);
        // 在正式项目中要对反回的格式有校验，对错误格式要抛出异常，在不校验的情况下下面这样写容易抛出空指针，但这里为了演示就简单写了
        String s = root.path("choices").get(0).get("message").path("tool_calls").get(0).path("function").path("arguments").toString();
        System.out.println("arguments的内容：" + s);
    }
}
