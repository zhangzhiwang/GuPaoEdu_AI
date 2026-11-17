package com.gupao.encapsulation.opt5.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gupao.encapsulation.opt5.exception.*;
import com.gupao.encapsulation.opt5.interceptor.HeaderInterceptor;
import com.gupao.encapsulation.opt5.interceptor.LoggerInterceptor;
import com.gupao.encapsulation.opt5.interceptor.RetryInterceptor;
import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import com.gupao.encapsulation.opt5.listener.StreamListener;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.TimeUnit;

import static com.gupao.encapsulation.opt5.constants.AIConstants.*;

/**
 * 访问 AI 大模型客户端基础类
 *
 * @Date 2026/10/17 4:59 PM
 */
public abstract class BaseAIClient implements AIClient {
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    public static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)// 连接超时
            .readTimeout(60, TimeUnit.SECONDS)// 读超时
            .writeTimeout(60, TimeUnit.SECONDS)// 写超时
            .callTimeout(90, TimeUnit.SECONDS)// 请求超时
            .retryOnConnectionFailure(true)// 失败重试
            .addInterceptor(new LoggerInterceptor())// 添加日志拦截器
//            .addInterceptor(new HeaderInterceptor())// 添加请求头拦截器
//            .addInterceptor(new RetryInterceptor(3))
            .connectionPool(new ConnectionPool(0, 5, TimeUnit.MINUTES))// 连接池设置
            .build();
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public ChatResponse chatNoneStream(ChatRequest request) throws Exception {
        if (request == null) {
            throw new RuntimeException("Param is empty!");
        }

        try(Response res = call(request);
            ResponseBody responseBody = res.body();) {
            checkResSuc(res);
            String resJson = responseBody.string();
            LOGGER.debug("返回的原始 json：{}", resJson);
            JsonNode resNode = MAPPER.readTree(resJson);
            String resText = getTextStream(resNode);
            return new ChatResponse(true, resText);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chatStream(ChatRequest request, StreamListener listener) {
        if (request == null || listener == null) {
            throw new RuntimeException("Param is empty!");
        }

        try(Response res = call(request);
            ResponseBody responseBody = res.body()) {
            checkResSuc(res);
            okio.BufferedSource source = responseBody.source();
            while (!source.exhausted()) {// exhaust v.耗尽，用光
                String line = source.readUtf8Line();
                LOGGER.debug("line:{}", line);
                if (StringUtils.isBlank(line) || !line.startsWith(SSE_DATA_PREFIX)) {
                    continue;
                }

                String json = line.substring(6);
                if (SSE_DONE.equals(json)) {
                    listener.done();
                    break;
                }

                JsonNode root = MAPPER.readTree(json);
                /*
                JsonNode取某个属性的值可以使用 get()方法，也可以使用 path()方法，二者的效果是一样的。只不过 get 方法在某属性不存在时返回 null，
                而 path 方法会返回一个特殊的节点叫MissingNode，以防止NPE。
                 */
                getTextNoneStream(listener, root);
            }
        } catch (Exception e) {
            listener.error(e);
        }
    }

    protected abstract void getTextNoneStream(StreamListener listener, JsonNode root) throws InterruptedException;

    protected abstract String getTextStream(JsonNode resNode);

    protected abstract Response call(ChatRequest request) throws Exception;

    protected void checkResSuc(Response response) throws Exception {
        if (response.isSuccessful()) {
            return;
        }

        int code = response.code();
        String msg = response.message();
        switch (code) {
            case 400:
                throw new BadRequestException("请求参数错误！code:" + code + "，msg：" + msg);
            case 401:
                throw new AuthenticationException("API KEY有误！code:" + code + "，msg：" + msg);
            case 429:
                throw new RateLimitException("访问过于频繁！code:" + code + "，msg：" + msg);
            default:
                if(code >= 500) {
                    throw new ServerException(code, msg);
                }
                throw new AIException(code, msg);
        }
    }
}
