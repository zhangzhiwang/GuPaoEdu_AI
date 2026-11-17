package com.gupao.encapsulation.opt5.client;

import com.gupao.encapsulation.opt5.model.ChatRequest;
import com.gupao.encapsulation.opt5.model.ChatResponse;
import com.gupao.encapsulation.opt5.listener.StreamListener;

/**
 * 访问 AI 大模型客户端接口
 *
 * @Date 2026/10/11 2:51 PM
 */
public interface AIClient {
	/*
	* 与大模型聊天（非流式）
	*
	* @param request
	* @return com.gupao.encapsulation.opt5.model.ChatResponse
	* @date 2026/10/17 6:01 PM
	*/
	ChatResponse chatNoneStream(ChatRequest request) throws Exception;

	/*
	* 与大模型聊天（流式）
	*
	* @param request
	* @param listener 监听器
	* @return void
	* @date 2026/10/17 6:02 PM
	*/
	void chatStream(ChatRequest request, StreamListener listener) throws Exception;
}
