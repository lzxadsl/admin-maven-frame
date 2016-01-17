package com.admin.system.service.impl;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.admin.system.service.IProducerService;

/**
 * 
 * @author LiZhiXian
 * @version 1.0
 * @date 2015-11-5 下午1:35:02
 */
@Service("pService")
public class ProducerService implements IProducerService{

	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void sendMessage(Destination destination, final String message){
		System.out.println("---------------生产者发送消息-----------------");
        System.out.println("---------------生产者发了一个消息：" + message);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
	}
	
	public void receiveMessage(Destination destination){
        TextMessage mess = (TextMessage) jmsTemplate.receive(destination);
        System.out.println("--------------------------------------------:"+mess);
	}
}
