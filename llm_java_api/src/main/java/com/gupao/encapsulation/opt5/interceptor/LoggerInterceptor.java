package com.gupao.encapsulation.opt5.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 日志拦截器
 *
 * @Date 2026/10/18 8:47 PM
 */
public class LoggerInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerInterceptor.class);

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 获取请求对象
        Request request = chain.request();
        LOGGER.info("发送请求，url：{}，method：{}", request.url(), request.method());
        long start = System.currentTimeMillis();
        // 放行请求
        Response response = chain.proceed(request);
        long end = System.currentTimeMillis();
        LOGGER.info("请求完成，响应状态码：{}，耗时：{}", response.code(), (end - start));
        return response;
    }
}
