package com.gupao.encapsulation.opt5.listener;

/**
 * 流式调用监听器接口
 *
 * @Date 2026/10/11 3:56 PM
 */
public interface StreamListener {
    /*
    * 响应消息处理
    *
    * @param text
    * @return void
    * @date 2026/10/17 6:15 PM
    */
    void message(String text);

    /*
    * 流式响应结束后做一些事情
    *
    * @param
    * @return void
    * @date 2026/10/17 6:16 PM
    */
    void done();

    /*
    * 错误处理
    *
    * @param e
    * @return void
    * @date 2026/10/17 6:42 PM
    */
    void error(Exception e);
}
