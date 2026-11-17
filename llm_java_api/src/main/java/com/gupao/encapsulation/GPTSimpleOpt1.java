package com.gupao.encapsulation;

import okhttp3.*;

/**
 * GPTSimple的第一个优化（optimize）版本
 *
 * @Date 2026/10/7 9:37 PM
 */
public class GPTSimpleOpt1 {
    // 优化：将url 提取为常量，也可以提取到环境变量中去
    private static final String URL = "https://api.openai-hk.com/v1/chat/completions";
    /*
    优化：将api key提取为常量，并从环境变量读取
    配置环境变量的方式有3种：
    1、在idea 里面编辑 Run Configuration，在 Build and run 下面的Environment variables 里面添加键值对：API_KEY=...
    2、在终端输入：export API_KEY=“...“，但是这个只对当前终端会话有效，关闭当前会话失效
    3、在~/.zshrc文件中加入：export API_KEY=“...“，然后 source ~/.zshrc，这个会永久生效。
    注意：第二种和第三种适合使用 java 命令的命令行方式运行程序，idea 里面读取不到，idea 里面运行程序只能使用方法 1。
     */
    private static final String API_KEY = System.getenv("API_KEY");

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
                "            \"content\": \"你是谁？\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), raw);// 媒体类型是 json

        // 构建请求对象
        Request request = new Request.Builder()
                .url(URL)
                .post(requestBody)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .build();

        // 发送请求，获取响应对象
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.string());
    }
}
