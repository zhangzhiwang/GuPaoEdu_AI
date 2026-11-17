package com.gupao.encapsulation.opt5.exception;

/**
 * 服务器异常
 *
 * @Date 2026/10/24 4:39 PM
 */
public class ServerException extends AIException {
    public ServerException(int code, String msg) {
        super(code, msg);
    }
}
