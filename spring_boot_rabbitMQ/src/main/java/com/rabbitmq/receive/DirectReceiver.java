package com.rabbitmq.receive;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.config.RabbitMQDirectConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * direct模式消费者
 * 
 * @author cm_wang
 *
 */
@Component
@Slf4j
public class DirectReceiver {

	// queues是指要监听的队列的名字
	@RabbitListener(queues = RabbitMQDirectConfig.DIRECT_QUEUE)
	public void receiverDirect(Object obj) {
		log.info("receiverDirectQueue收到消息" + obj.toString());
	}
}
