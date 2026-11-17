package com.gupao.encapsulation.opt5.interceptor;

import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 重复请求拦截器
 *
 * @Date 2026/10/24 3:49 PM
 */
@AllArgsConstructor
public class RetryInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RetryInterceptor.class);
    private int retryCount;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        try {
            response = chain.proceed(request);
            if (response.code() == 200) {
                return response;
            }

            throw new RuntimeException("访问出现异常！");
        } catch (Exception e) {
            if (response != null) {
                LOGGER.error("访问出错：code:{}，msg:{}", response.code(), response.message());
            }
            while (retryCount > 0) {
                // 指数退避
                try {
                    long sleep = (long) Math.pow(2, retryCount) * 1000;
                    Thread.sleep(sleep);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                LOGGER.info("请求失败！剩余重试次数{}次", --retryCount);
                response = intercept(chain);
            }
            throw new IOException("已达到最大重试次数，请求失败！");
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
