package com.gupao.encapsulation.opt5.exception;

/**
 * 认证异常
 *
 * @Date 2026/10/24 4:20 PM
 */
public class AuthenticationException extends AIException {
    public AuthenticationException(String msg) {
        super(401, msg);
    }
}
