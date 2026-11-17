package com.gupao.encapsulation.opt5.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2026/10/24 5:03 PM
 */
@AllArgsConstructor
@Getter
public enum ContentTypeEnum {
    APPLICATION_JSON(1, "application/json", "json格式");

    private int code;
    private String value;
    private String desc;
}
