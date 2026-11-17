package com.gupao.encapsulation.opt5.exception;

/**
 * 请求异常
 *
 * @Date 2026/10/24 4:22 PM
 */
public class BadRequestException extends AIException {
    public BadRequestException(String msg) {
        super(400, msg);
    }
}
