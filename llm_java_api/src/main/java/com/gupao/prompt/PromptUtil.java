package com.gupao.prompt;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 提示词工具类
 *
 * @Date 2026/10/25 5:20 PM
 */
public class PromptUtil {
    private PromptUtil() {
    }

    /**
     * 解析模版，将模版中的占位符进行替换
     * @param template
     * @param paramMap
     * @return
     */
    public static String resolveTemplate(String template, Map<String, Object> paramMap) {
        if (StringUtils.isBlank(template) || paramMap == null || paramMap.isEmpty()) {
            throw new RuntimeException("参数有误！");
        }

        for (String key : paramMap.keySet()) {
            template = template.replace("{" + key + "}", String.valueOf(paramMap.get(key)));
        }

        return template;
    }
}
