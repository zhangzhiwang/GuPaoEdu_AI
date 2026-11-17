package com.gupao.encapsulation.opt5.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 请求头拦截器
 *
 * @Date 2026/10/24 2:23 PM
 */
public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
//                .addHeader("Authorization", "Bearer hk-2wxvpj100006505229b6e11880807b143716c67448d00abb")
//                .addHeader("Content-Type", "application/json")
                .build();
        // 放行请求
        Response response = chain.proceed(request);
        return response;
    }
}
