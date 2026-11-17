package com.gupao.encapsulation.opt5.exception;

/**
 * 重试异常
 *
 * @Date 2026/10/24 4:38 PM
 */
public class RetryableException extends AIException {
    public RetryableException(int code, String msg) {
        super(code, msg);
    }
}
