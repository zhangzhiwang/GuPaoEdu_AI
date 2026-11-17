package com.gupao.prompt;

/**
 * @Date 2026/10/25 6:22 PM
 */
public class PromptBuilder {
    private static final String COMMON_TEMPLATE = "你是一名%s。\n" +
            "                     请根据用户画像推荐商品。\n" +
            "                     背景:\n" +
            "                     %s\n" +
            "                \n" +
            "                     任务：\n" +
            "                \n" +
            "                     %s\n" +
            "                \n" +
            "                     约束：\n" +
            "                \n" +
            "                     %s\n" +
            "                \n" +
            "                     输出的格式:\n" +
            "                     %s。";
    private String role;
    private String context;
    private String task;
    private String constraint;
    private String format;

    public PromptBuilder role(String role) {
        this.role = role;
        return this;
    }

    public PromptBuilder context(String context) {
        this.context = context;
        return this;
    }

    public PromptBuilder task(String task) {
        this.task = task;
        return this;
    }

    public PromptBuilder constraint(String constraint) {
        this.constraint = constraint;
        return this;
    }

    public PromptBuilder format(String format) {
        this.format = format;
        return this;
    }

    public String build() {
        return String.format(COMMON_TEMPLATE, role, context, task, constraint, format);
    }
}
