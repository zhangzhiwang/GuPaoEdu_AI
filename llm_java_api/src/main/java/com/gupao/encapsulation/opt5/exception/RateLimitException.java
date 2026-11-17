package com.gupao.encapsulation.opt5.exception;

/**
 * 请求频繁异常
 *
 * @Date 2026/10/24 4:26 PM
 */
public class RateLimitException extends AIException {
    public RateLimitException(String msg) {
        super(429, msg);// HTTP状态码429表示：Too Many Requests
    }
}
