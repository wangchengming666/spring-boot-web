package com.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.config.RabbitMQTopicConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * topic模式消费者
 * 
 * @author cm_wang
 *
 */
@Component
@Slf4j
public class TopicReceiver {

	// queues是指要监听的队列的名字
	@RabbitListener(queues = RabbitMQTopicConfig.TOPIC_QUEUE)
	public void receiveTopic(Object obj) {
		log.info("receiveTopic收到消息:" + obj.toString());
	}
}
