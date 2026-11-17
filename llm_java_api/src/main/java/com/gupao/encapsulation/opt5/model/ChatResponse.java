package com.gupao.encapsulation.opt5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 本类是对大模型响应参数的封装
 *
 * @Date 2026/10/11 2:48 PM
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {
    /** 是否响应成功 */
    private boolean isSuc;
    /** 响应内容 */
    private String content;
}
