package com.gupao.encapsulation.opt5.exception;

/**
 * AI请求根异常类
 *
 * @Date 2026/10/24 4:17 PM
 */
public class AIException extends RuntimeException {
    private int code;

    public AIException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
