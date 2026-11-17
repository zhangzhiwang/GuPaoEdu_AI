package com.gupao.prompt;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 提示词工厂类
 *
 * @Date 2026/10/25 6:02 PM
 */
public class PromptFactory {
    private PromptFactory() {}

    public static String create(String promptName) {
        if (StringUtils.isBlank(promptName)) {
            throw new RuntimeException("参数有误！");
        }

        // 可以把模板配置到配置文件里面，从配置里面读取，这里只是简单地实现
        switch(promptName) {
            case "JAVA_TEMPLATE":
                return "你是一名{role}\n" +
                        "                \n" +
                        "                请从以下角度分析：\n" +
                        "                \n" +
                        "                1. 技术成长路线\n" +
                        "                2. AI发展趋势\n" +
                        "                3. 职业规划\n" +
                        "                \n" +
                        "                当前职业：\n" +
                        "                \n" +
                        "                {job}";
            case "SQL_PROMPT":
                return " 根据需求生成SQL:\n" +
                        "            {requirement}";
            case "E-COMMERCE":
                return "你是一名资深电商运营专家。\n" +
                        "                     请根据用户画像推荐商品。\n" +
                        "                     用户信息：\n" +
                        "                \n" +
                        "                     年龄：{age}\n" +
                        "                \n" +
                        "                     性别：{gender}\n" +
                        "                \n" +
                        "                     职业：{job}\n" +
                        "                \n" +
                        "                     兴趣爱好：\n" +
                        "                \n" +
                        "                     {interest}\n" +
                        "                \n" +
                        "                     推荐商品：\n" +
                        "                \n" +
                        "                     {product}\n" +
                        "                \n" +
                        "                     要求：\n" +
                        "                \n" +
                        "                     1. 结合用户职业特点\n" +
                        "                     2. 结合用户兴趣爱好\n" +
                        "                     3. 突出商品卖点\n" +
                        "                     4. 生成100字以内推荐语\n" +
                        "                     5. 输出自然流畅\n" +
                        "                \n" +
                        "                     请直接输出推荐文案。";
            default:
                return "";
        }
    }
}
