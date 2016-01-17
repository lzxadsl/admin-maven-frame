package com.admin.system.service;

import javax.jms.Destination;

/**
 * 消息生产者接口
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-11-5 上午11:55:53
 */
public interface IProducerService {
	public void sendMessage(Destination destination, final String message);
	public void receiveMessage(Destination destination);
}
