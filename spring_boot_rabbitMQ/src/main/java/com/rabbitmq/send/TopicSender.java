package com.rabbitmq.send;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.config.RabbitMQTopicConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * topic模式生产者
 * 
 * @author cm_wang
 *
 */
@Component
@Slf4j
public class TopicSender {

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * topic 模式
	 */
	public void sendTopic(Object obj) {
		
		log.info("sendTopic已发送消息");
		// 第一个参数：TopicExchange名字
		// 第二个参数：Route-Key
		// 第三个参数：要发送的内容
		this.amqpTemplate.convertAndSend(RabbitMQTopicConfig.TOPIC_EXCHANGE, "rabbitmq.message", obj);
	}
}
